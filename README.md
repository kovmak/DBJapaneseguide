# DBStoryManager

DBStoryManager is a lightweight Python library for managing stories in a database.

## Features

- **Story Creation**: Easily create new stories and add chapters.
- **Story Retrieval**: Retrieve full stories or specific chapters.
- **Database Integration**: Works seamlessly with various SQL databases.
- **Simple API**: Intuitive methods for adding, updating, and querying stories.

## Create a new story
story_id = db_manager.create_story(title="My First Story")

## Add chapters to the story
db_manager.add_chapter(story_id, title="Chapter 1", content="Once upon a time...")
db_manager.add_chapter(story_id, title="Chapter 2", content="And they lived happily ever after.")

## Retrieve a specific story
```
story = db_manager.get_story(story_id)
print(f"Title: {story['title']}")
for chapter in story['chapters']:
    print(f"Chapter {chapter['chapter_number']}: {chapter['title']}")
    print(chapter['content'])
```
## Update a story
```
db_manager.update_story(story_id, title="Updated Story Title")
```
## Delete a story
```
db_manager.delete_story(story_id)
```
## Contributing
### Contributions are welcome! Please feel free to fork this repository and submit pull requests.



## License

This project is licensed under the [MIT License](LICENSE).
