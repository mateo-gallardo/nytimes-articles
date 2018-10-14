package com.mateogallardo.nytimesarticles.data.api

class ArticlesApiResponse(var response: Response)

class Response(var docs: Array<Doc>)

class Doc(var _id: String, var word_count: Int, var multimedia: Array<Multimedia>, var headline: Headline)

class Headline(var main: String)

class Multimedia(var subtype: String, var url: String)
