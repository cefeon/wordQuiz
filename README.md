wordQuiz
======

First start instruction
------
1. Before first start change resources/application.properties property _spring.jpa.hibernate.ddl-auto=none_ to _spring.jpa.hibernate.ddl-auto=create_
2. Load data with /loadCSV

Routes to use
------

```translations/number_of_page``` Return paged translations, where n is number of page (so far 20 records per page).

```[source_language]/[destination_language]/word_to_translate``` Translate word from source language to destination language.

```compare/[source_language]/[destination_language]/word_to_test``` Compare words to test with word from database, and return results ordered by Jaro-Wrinkler score (with Jaro-Wrinkler score).

Languages to use
------

Currently available languages with words used as source and destination language.

| Language      | Word used   |
| ------------- |:-----------:|
| Polish        | pl          |
| GB English    | en          |
