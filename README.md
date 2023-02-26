# Android_develop_final_project
my first android app - todo list

1)	This Todo List App has two Activities (MainActivity and todoListActivity).
2)	Navigation between the 2 screens: go to todoListActivity by clicking Button-CHECK TASKS UNDONE, go back to MainActivity by clicking Button-BACK.
3)	MainActivity displays some data (id, name, email, phone, city of a specific user) fetched from https://jsonplaceholder.typicode.com/users , todoListAc-tivity displays some data (todos that the user has not completed yet) fetched from https://jsonplaceholder.typicode.com/todos.
4)	The activity data is not lost during Activity lifecycle, like when rotating the device or back from email/call apps.
5)	All strings are localized for English, Chinese and Finnish.
6)	There are no hard-coded strings.
7)	After entering an id (number format, 1~10) in the given field, App users can click Button-GO and obtain the information of that user (initial user 1).
8)	If a invalid user id (outside 1-10) is given, a toast-User Not Found… will pop.
9)	The App allows to send email to or dial the given user by clicking corre-sponding button-SEND EMAIL, MAKE A CALL.
