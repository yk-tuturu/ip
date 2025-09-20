# MikuBot User Guide

![Product Screenshot](Ui.png)

### Welcome to MikuBot! Chat with the miku and let miku manage your tasks for you!

## Adding a basic task `todo`
Command usage: `todo <task>`

For example, you can add a task by typing `todo finish my homework`

Expected output: 
```aiignore
Miku has added this task to your list!
    [T][] task
You now have 1 task in your list
```

## Adding a task with a deadline `deadline`
Command usage: `deadline <task> /by <time>`

For example, you can add a deadline task with `deadline 2103t ip /by 19/9/2025 2359`

The deadline time needs to be provided as a proper date. Various formats are accepted (eg DD/MM/YYYY, YYYY-MM-DD). 
Time can also optionally be provided in either 12h or 24h format. If no time is provided, defaults to 12:00am.

Expected output: 
```aiignore
Miku has added this task to your list!
    [D][] 2103t ip (by: Sep 19 2025 11:59pm)
You now have 1 task in your list
```

## Adding a task with a start and end time `event`
Command usage: `event <task> /from <time> /to <time>`

For example, you can add an event task with `event go to school /from 23/9/2025 9:00am /to 23/9/2025 6:00pm`

Both start and end times need to be provided as a valid date. 

Expected output: 
```aiignore
Miku has added this task to your list!
    [E][] go to school (from: Sep 23 2025 9:00am to: Sep 23 2025 6:00pm)
You now have 1 task in your list
```

## Adding a durational task with no fixed start or end `duration`
Command usage: `duration <task> /in <duration>`

For example, you can add a durational task as `duration do hw /in 2 hours`

Expected output: 
```aiignore
Miku has added this task to your list!
    [F][] do hw (needs 2 hours)
You now have 1 task in your list
```

(F stands for fixed duration task)


## Listing tasks `list`
Command usage: `list`

Lists out all current tasks

```aiignore
1. [T][] task1
2. [T][] task2
```

## Finding tasks `find`
Command usage: `find <keyword>`

Finds task by searching by keyword. Matches keyword to the value of the task.

```aiignore
Miku found the following tasks: 
1. 2103t ip (by: Sep 19 2025 11:59pm)
```

## Deleting tasks `delete`
Command usage `delete <index>`

Deletes a task by their index in the current tasklist

```aiignore
Miku has deleted the task: 
    [T][] task
Now you have 2 tasks remaining
```

## Marking and unmarking tasks as done `mark` & `unmark`
Command usage: `mark <index>` || `unmark <index>`

Marks the task at the index as done. 

```aiignore
Omedetou! You've finished a task:
    [T][X] task
```

```aiignore
Aw man! You still haven't finished the task:
    [T][] task
```

## Getting help 
Command usage: `help`

Prints out a list of commands and their usages

## Exiting
Command usage: `bye`

Exits the program
