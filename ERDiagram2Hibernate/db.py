from sqlalchemy import MetaData
from sqlalchemy.schema import CreateTable
import sqlalchemy
from sqlalchemy_schemadisplay import create_schema_graph
import os

NEON_CONNECTION_STRING = os.getenv('NEON_CONNECTION_STRING')
engine = sqlalchemy.create_engine(
    url=NEON_CONNECTION_STRING, pool_pre_ping=True, pool_recycle=300
)

metadata = MetaData()
metadata.reflect(bind=engine)

graph = create_schema_graph(
    engine=engine,
    metadata=metadata,
    show_datatypes=True,  # Show column data types
    show_indexes=True,  # Show indexes (not relevant for all databases)
    rankdir='TB',  # Left to right layout
    concentrate=False  # Avoid node overlapping
)

graph.write_jpeg("/Users/dheerajhegde/Documents/schema.jpeg")

"""for met in dir(graph):
    print(met)

print(graph.create_canon())
print(graph.to_string())"""

"""for table in metadata.sorted_tables:
    print(CreateTable(table).compile(engine))"""

