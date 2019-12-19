# LanguageStructGen
Public repository for the generator of JSON with vocabulary for the videogame Hamsun's amulet

This code has the necessary logic to compute the WordNet "MCR" ("Multilingual Central Repository") and the "WikiCorpus" in order to generate
JSON files which store synsets or sets of words with their morphological data. The actual code works with the English and the Spanish
languages.

To be able to use this program, It is necessary to have deployed the MCR's database with It's authentification data stored in 
the "configuration.properties"file of the project, and the "WikiCorpus" files located in the folders:
resources-->TextData-->ENG/SPA (one folder for each language)
It is also important to have the "WikiCorpus" files converted to UTF-8.

In the following links you can download the aforementioned resources.

Multilingual Central Repository: http://adimen.si.ehu.es/web/MCR

WikiCorpus: https://www.cs.upc.edu/~nlp/wikicorpus/

And finally, the link to Hamsun's Amulet:
https://github.com/JorViteri/Hamsun-s-amulet
