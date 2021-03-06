# New York Times Articles

_built in Kotlin_

> Gif loading...

![Features showcase](https://thumbs.gfycat.com/DimwittedWholeAmphibian-size_restricted.gif)

Notes:
- Some articles don't have thumbnails by design
- The loading indicator is shown when the offline request is made, but the DB response is so fast you can barely see it

## TODO
- [x] Decouple repository from DB threading
   - It shouldn't know that DB needs to be called in a different thread and it'll help with testing
- [x] ~~Use Mockito to mock the observer in the tests~~
   - Doesn't work very well with kotlin...
- [ ] Get Kodein to work for dependency injection and remove Injector class
- [ ] Add pagination with infinite scroll to the articles list
- [ ] Save the images either to the DB or the filesystem, instead of relying solely on Picasso
- [ ] Set a min latency for the DB query, so the screen isn't updated too quickly
