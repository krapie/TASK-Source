import './App.css';
import { useEffect, useState } from 'react';

function App() {
  const [ taskList, setTaskList ] = useState(null);
  
  // CREATE
  function createTask() {
    const newTask = {
      content : "task create",
      isDone : false
    }

    fetch('http://localhost:8080/api/task', {
      method : 'POST',
      headers: {
        'content-type' : 'application/json'
      },
      body : JSON.stringify(newTask)
    })
    .then((response) => response.json())
    .then((data) => {
      console.log("Created: ", data);
    })
      
  } 
  
  // Read
  function fetchTask() {
    fetch('http://localhost:8080/api/task')
        .then((response) => response.json())
        .then((data) => {
          console.log("TASKS: ", data);
          setTaskList(data);
        });
  }

  // Update
  function updateTask() {
    const updatedTask = {
      content : "task update",
      isDone : true
    }

    fetch(`http://localhost:8080/api/task/1`, {
            method : 'PUT',
            headers : {
                'content-type' : 'application/json'
            },
            body : JSON.stringify(updatedTask)
        })
        .then((response) => response.json())
        .then((data) => {
          console.log("Updated ID: ", data);
        });
  }

  // Delete
  function deleteTask() {
    fetch(`http://localhost:8080/api/task/1`, {
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
      <h1>TASK</h1>
      <hr></hr>
      <p>TASK Client-Server API Test</p>

      <button onClick={createTask}>Create</button>
      <button onClick={fetchTask}>Read</button>
      <button onClick={updateTask}>Update</button>
      <button onClick={deleteTask}>Delete</button>
    </div>
  );
}

export default App;
