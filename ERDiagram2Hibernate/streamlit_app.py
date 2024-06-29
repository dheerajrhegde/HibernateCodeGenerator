import sys

from langgraph.graph import StateGraph, END
from typing import TypedDict, Annotated
from langchain_core.messages import AnyMessage, SystemMessage, HumanMessage, ToolMessage
from langchain_community.tools.tavily_search import TavilySearchResults
import operator, os
from langchain.agents import tool
from langchain.pydantic_v1 import BaseModel, Field
from sqlalchemy_schemadisplay import create_schema_graph
from sqlalchemy.schema import CreateTable
from sqlalchemy import MetaData
from langchain_openai.chat_models import ChatOpenAI
import re, sqlalchemy
from langchain_mistralai.chat_models import ChatMistralAI
from langchain_core.runnables.config import RunnableConfig

NEON_CONNECTION_STRING = os.getenv('NEON_CONNECTION_STRING')
engine = sqlalchemy.create_engine(
    url=NEON_CONNECTION_STRING, pool_pre_ping=True, pool_recycle=300
)
model = ChatOpenAI(model='gpt-4o', openai_api_key=os.getenv("OPENAI_API_KEY"))

# Data model
class code(BaseModel):
    """Code output"""
    code: str = Field(description="Full code of a Hibernate entity including import statemens.")

class xml(BaseModel):
    """XML output"""
    code: str = Field(description="Full XML for hibernate.cfg.xml")


class AgentState(TypedDict):
    messages: Annotated[list[AnyMessage], operator.add]
    def __init__(self):
        self.recursion_count = 0

class Agent:
    def __init__(self, model, tools, system=""):
        self.system = system
        graph = StateGraph(AgentState)
        graph.add_node("llm", self.call_openai)
        graph.add_node("action", self.take_action)
        graph.add_conditional_edges("llm", self.exists_action, {True: "action", False: END})
        graph.add_edge("action", "llm")
        """graph.add_conditional_edges(
                from_node="llm",
                to_node="action",
                condition=lambda state: state.recursion_count >= graph.config.get('recursion_limit', 5)
        )"""
        #graph.config.get('recursion_limit', 100)
        graph.set_entry_point("llm")

        self.graph = graph.compile()
        self.tools = {t.name: t for t in tools}
        self.model = model.bind_tools(tools)

    def call_openai(self, state: AgentState):
        # print("Calling open AI")
        messages = state['messages']
        if self.system:
            messages = [SystemMessage(content=self.system)] + messages
        message = self.model.invoke(messages)
        return {'messages': [message]}

    def exists_action(self, state: AgentState):
        # print("in exists action")
        result = state['messages'][-1]
        return len(result.tool_calls) > 0

    def take_action(self, state: AgentState):
        tool_calls = state['messages'][-1].tool_calls
        results = []
        for t in tool_calls:
            # print(f"Calling: {t}")
            result = self.tools[t['name']].invoke(t['args'])
            results.append(ToolMessage(tool_call_id=t['id'], name=t['name'], content=str(result)))

        # print("Back to the model!")
        return {'messages': results}

@tool
def get_schema():
    """
    Get the schema of the database to use for creating and running the SQL query.
    """
    NEON_CONNECTION_STRING = os.getenv('NEON_CONNECTION_STRING')
    engine = sqlalchemy.create_engine(
        url=NEON_CONNECTION_STRING, pool_pre_ping=True, pool_recycle=300
    )

    metadata = MetaData()
    metadata.reflect(bind=engine)

    """graph = create_schema_graph(
        engine=engine,
        metadata=metadata,
        show_datatypes=True,  # Show column data types
        show_indexes=True,  # Show indexes (not relevant for all databases)
        rankdir='LR',  # Left to right layout
        concentrate=False  # Avoid node overlapping
    )

    return graph.create_plain()"""
    schema = """ """
    for table in metadata.sorted_tables:
        schema += str(CreateTable(table).compile(engine))
        schema += "\n"
    return schema

class Entity(BaseModel):
    entity_definition: str = Field(..., description="Database table definition to create hibernate code")
    guidance: str = Field(..., description="Specific guidance to use while creating the hibernate Java code")

@tool(args_schema=Entity)
def create_hibernate_code(entity_definition: str, guidance: str):
    """
    Create hibernate code for the given entity definition
    """
    api_key = os.environ["MISTRAL_API_KEY"]
    model_name = "codestral-latest"
    model = ChatMistralAI(api_key=api_key, model=model_name)

    prompt = f"""
        Create hibernate code for the entity definition: {entity_definition}
        - Make sure to comment the code as per Java coding standards
        - Ensure the code is correctly formatted and follows the standard Hibernate conventions
        
        Use below guidance as you create the code
        {guidance}
    
    Your response should only be the Java code for the entity definition. Nothing else. 
    """
    print("creating hibernate code... for", entity_definition)

    code_gen_chain = model.with_structured_output(code)
    response = code_gen_chain.invoke(entity_definition)

    print(response)
    return response

class Config(BaseModel):
    database_url: str = Field(description="Database connection URL to use for hibernate.cfg.xml db properties")
    entity_list: list = Field(description="List of entity class names to add as mapping to hibernate.cfg.xml")

@tool(args_schema=Config)
def create_hibernate_config(database_url: str, entity_list: str):
    """
    Create hibernate configuration file for the application
    """
    api_key = os.environ["MISTRAL_API_KEY"]
    model_name = "codestral-latest"
    model = ChatMistralAI(api_key=api_key, model=model_name)

    prompt = f"""
        You are a developer who is writing a configuration file for an Hibernate module/package.
        
        Create a hibernate.cfg.xml with database connection properties coming from {database_url}.
        Do not add parameter to regenerate the database schema. Only read from the schema.
        For each class listed in {entity_list}, add a mapping XML element into the hibernate.cfg.xml.
        
        You should have only the XML and nothing else in output
    """
    print("creating hibernate config... ")
    code_gen_chain = model.with_structured_output(xml)
    response = code_gen_chain.invoke(database_url)
    print(response)
    return response

class CodeFile(BaseModel):
    file_name: str = Field(..., description="Name of the file")
    content: str = Field(..., description="Content of the file")
@tool(args_schema=CodeFile)
def save_file(file_name: str, content: str):
    """
    Save the generated code to a file
    """
    with open(file_name, "w") as f:
        f.write(content)
    return "File saved successfully"

class Directory(BaseModel):
    directory_path: str = Field(..., description="Path of the directory to create")
@tool(args_schema=Directory)
def create_directory(directory_path: str):
    """
    Create a directory at the given path
    """
    os.makedirs(directory_path, exist_ok=True)
    return "Directory created successfully"

@tool
def get_db_connection_details():
    """
    Get the connection details for the database
    """
    return os.getenv('NEON_CONNECTION_STRING')

import subprocess
class MavenRun(BaseModel):
    command: str = Field(..., description="Maven command to run. Example 'clean install', 'compile', 'test")
    project_dir: str = Field(..., description="Path to the directory containing the Maven project")

@tool(args_schema=MavenRun)
def run_maven_command(command, project_dir):
    """
    Executes a Maven command in the specified project directory.

    :param command: Maven command to run (e.g., 'clean install').
    :param project_dir: Path to the directory containing the Maven project.
    """
    try:
        # Construct the full Maven command
        full_command = f"/opt/homebrew/Cellar/maven/3.9.7/bin/mvn {command}"

        # Run the command and capture the output
        result = subprocess.run(
            full_command,
            cwd=project_dir,
            shell=True,
            check=True,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            universal_newlines=True
        )

        # Print the output and error (if any)
        print("Output:\n", result.stdout)
        if result.stderr:
            print("Errors:\n", result.stderr)

        print(f"Maven command '{command}' executed successfully.")

    except subprocess.CalledProcessError as e:
        print(f"An error occurred while running the Maven command: {e}")
        print(f"Return code: {e.returncode}")
        print(f"Output: {e.output}")
        print(f"Errors: {e.stderr}")
        return e.returncode, e.output, e.stderr

class CreateProject(BaseModel):
    base_dir: str = Field(..., description="Base directory for the project")
    project_name: str = Field(..., description="Name of the project")
    pom_content: str = Field(..., description="Content of the pom.xml file")

@tool(args_schema=CreateProject)
def create_maven_project(base_dir, project_name, pom_content):
    """
    Create a Maven project with the specified name and pom.xml content in the given base directory.
    """
    # Create the base directory for the project
    print("Creating maven project")
    project_dir = os.path.join(base_dir, project_name)
    os.makedirs(project_dir, exist_ok=True)

    # Define standard Maven directories
    directories = [
        "src/main/java/com/github/dheerajhegde/hibernate",
        "src/main/resources",
        "src/test/java/com/github/dheerajhegde/hibernate",
        "src/test/resources"
    ]

    # Create the directories
    for directory in directories:
        os.makedirs(os.path.join(project_dir, directory), exist_ok=True)

    # Write the pom.xml file
    pom_file_path = os.path.join(project_dir, "pom.xml")
    with open(pom_file_path, 'w') as pom_file:
        pom_file.write(pom_content)

    print(f"Project {project_name} created successfully at {project_dir}")

maven_project_prompt = """
    Create one maven project with Java 19, Hibernate 5.5.7, and JUnit 5. Project name is {project_name} and directory under which to create project is {project_root}
        - Create a maven pom.xml file for hibernate, junit, and other dependencies
        - tool provided is 'create_maven_project'
        - add org.postgresql as a dependency in the pom.xml file
        - Move onto next steps once the maven project is created. 
"""

prompt = """
        You are an coding assistant with expertise in writing Java Code. 
        
        Then, follow the instructions below:
        2. Understand the database schema provided to you as a text file
            - Identify Understand our each entity in the schema 
            - Identify the relationshio between database entities
        3. Before generating the java code, you need to think about
            - what packages you need to import
            - add @Column annotaion to each attribute definition
            - add @Table annotation for each class
            - add all other required annotations to make this work 
            - what relationships you need to define
            - what seter and getter methods you need to create
            - what hibernate mapping files you need to create and the mapping from class files to database tables
        4. Generate the Hibernate code for each entity in the schema. Guidance foor you:
            - Make sure you create Hibernate java code for every DB table. Retry in case code is 'None'
            - Use Java 19
            - Use Hibernate 5.5.7
            - Use Maven for dependency management
            - Use Maven file structure for the project
            - Use java structure of com.github.dheerajhegde.hibernate under {project_root}/{project_name}/src/main/java
            - Include the necessary imports for the Hibernate code
            - include the package statement (package com.github.dheerajhegde.hibernate)  at the start of Java file
            - Add a constructor that takes each attribute as an argument
            - Add a default no argument constructor 
            - Add setter and getter methods for each attribute
            - include the necessary annotations for the Hibernate code
            - Ensure the relationships between entities are correctly defined
            - Ensure the code is correctly formatter (new line, indentation)  and follows the standard Hibernate conventions
        5. Save each generated Hibernate code in a separate file in the {project_root}/{project_name}/src/main/java/com/github/dheerajhegde/hibernate folder
        6. Create a hibernate.cfg.xml file for each entity you have created in the previous step
            - Database connection should be in  hibernate.cfg.xml. Get connection details from the tools provided to you
            - create hibernate.cfg.xml in src/main/resources under project folder
            - use version 1.18.30 of org.projectlombok
        7. Create the hibernate mapping file for each entity. Store it in same folder as the hibernate code. Should be included in the hibernate.cfg.xml file
        8. Once Hibernate code is generated for all entities, compile and build the maven project and make sure it is error-free
            - Fix any errors that are detected during compilation
            - Incase of package errors, go back and update the POM.xml file to include the necessary dependencies
        9. Generate the documentation for the Hibernate code
            - use generally accepted conventions for documenting Hibernate code
        10. Create a Java that uses Hibernate Sessionfactory, Session, query to get Customer data from the database.
            - This generated java file should be named App.java and should be in {project_root}/{project_name}/src/main/java folder
            
        validation:
        1. Check if each Hibernate code you generate has setter and getter methods for each attribute of the entity
            - If not, go back and update the code to include the necessary setter and getter methods
        2. Confirm hibernate.cfg.xml is in src/main/resources folder and has all the necessary connection details and mappings
            
        Your response should be 
        - the list names of the files you created 
        - Maven Build results
    """
tools = [get_schema, create_hibernate_code, save_file, get_db_connection_details, run_maven_command, create_maven_project, create_hibernate_config]
project_root = "/Users/dheerajhegde/Documents/Code/Java"
project_name = "HibernateProject"
tools = [create_maven_project]
maven_prompt_text = maven_project_prompt.format(project_root=project_root, project_name=project_name)
abot = Agent(model, tools, system=maven_prompt_text)
thread = {"configurable": {"thread_id": "1"}}
response = abot.graph.invoke(input={"messages": [HumanMessage(content=[{"type": "text", "text": "Create hibernate code for all tables in DB schema. You have been given the tools to get the entity definitionb, etc."}])], "thread": thread})
print(response)

i = input("Okay to continue y/n")
if i == 'N':
    sys.exit()

tools = [get_schema, create_hibernate_code, save_file, get_db_connection_details, run_maven_command, create_hibernate_config]
prompt_text = prompt.format(project_root=project_root, project_name=project_name)
config = RunnableConfig(
            recursion_limit=50,  # Set the recursion limit
        )
abot = Agent(model, tools, system=prompt_text)
thread = {"configurable": {"thread_id": "1"}}
response = abot.graph.invoke(config=config, input={"messages": [HumanMessage(content=[{"type": "text", "text": "Create hibernate code for all tables in DB schema. You have been given the tools to get the entity definitionb, etc."}])], "thread": thread})
print(response)