因为写起来确实麻烦，这里我准备了一些测试语句，可以再运行四个图时使用
其他四个txt是针对四个图写的测试样例，可以拿来直接用


<<GraphPoet>>
addVertex:
Vertex = <"h", "Word">
Vertex = <"i", "Word">

addEdge:
Edge = <"E18", "WordNeighborhoodEdge", "1", "h", "a", "Yes">
Edge = <"E17", "WordNeighborhoodEdge", "1", "i", "i", "Yes">

<<SocialNetwork>>
addVertex:
Vertex = <"Chen", "Person", <"M", "22">>
Vertex = <"Sui", "Person", <"F", "23">>

addEdge:
Edge = <"E5", "CommentTie", "0.6", "Ren", "Chen", "Yes">
Edge = <"E6", "CommentTie", "0.6", "Sui", "Chen", "Yes">

<<NetworkTopology>>
addVertex:
Vertex = <"v5", "Computer", <"192.168.199.8">>
Vertex = <"v6", "Computer", <"192.168.199.11">>

addEdge:
Edge = <"e5", "NetworkConnection", "80", "v5", "v3", "No">
Edge = <"e6", "NetworkConnection", "60", "v6", "v3", "No">

<<MovieGraph>>
addVertex:
Vertex = <"m3", "Movie", <"1997", "Japan", "7.7">>
Vertex = <"a5", "Actor", <"22", "F">>

addEdge:
Edge = <"m3a5", "MovieActorRelation", "100", "m3", "a5", "No">
Edge = <"m3a1", "MovieActorRelation", "50", "m3", "a1", "No">


