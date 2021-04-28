import { useEffect, useState } from 'react';

function TodoTest() {
  const [ todoList, setTodoList ] = useState(null);
  
  // CREATE
  function createTodo() {
    const newTodo = {
      content : "todo create",
      isDone : false
    }

    fetch('http://localhost:8080/api/todo', {
      method : 'POST',
      headers: {
        'content-type' : 'application/json'
      },
      body : JSON.stringify(newTodo)
    })
    .then((response) => response.json())
    .then((data) => {
      console.log("Created: ", data);
    })
      
  } 
  
  // Read
  function fetchTodo() {
    fetch('http://localhost:8080/api/todo')
        .then((response) => response.json())
        .then((data) => {
          console.log("Todo Items: ", data);
          setTodoList(data);
        });
  }

  // Update
  function updateTodo() {
    const updatedTodo = {
      content : "todo update",
      isDone : true
    }

    fetch(`http://localhost:8080/api/todo/1`, {
            method : 'PUT',
            headers : {
                'content-type' : 'application/json'
            },
            body : JSON.stringify(updatedTodo)
        })
        .then((response) => response.json())
        .then((data) => {
          console.log("Updated ID: ", data);
        });
  }

  // Delete
  function deleteTodo() {
    fetch(`http://localhost:8080/api/todo/1`, {
            method : 'DELETE',
            headers : {
                'content-type' : 'application/json'
            }
        })
        .then((response) => response.json())
        .then((data) => {
          console.log("Deleted ID: ", data);
        });
  }

  return (
    <div>
      <h1>TODO</h1>
      <hr></hr>
      <p>TASK-todo Client-Server API Test</p>

      <button onClick={createTodo}>Create</button>
      <button onClick={fetchTodo}>Read</button>
      <button onClick={updateTodo}>Update</button>
      <button onClick={deleteTodo}>Delete</button>
    </div>
  );
}

export default TodoTest;
