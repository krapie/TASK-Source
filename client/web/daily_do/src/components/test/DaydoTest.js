import { useEffect, useState } from 'react';

function DaydoTest() {
  const [ daydoList, setDaydoList ] = useState(null);
  
  // CREATE
  function createDaydo() {
    const newDaydo = {
      day : 0,
      content : "sunday todo"
    }

    fetch('http://localhost:8080/api/daydo', {
      method : 'POST',
      headers: {
        'content-type' : 'application/json'
      },
      body : JSON.stringify(newDaydo)
    })
    .then((response) => response.json())
    .then((data) => {
      console.log("Created: ", data);
    })
      
  } 
  
  // Read
  function fetchDaydo() {
    fetch('http://localhost:8080/api/daydo')
        .then((response) => response.json())
        .then((data) => {
          console.log("Daydo Items: ", data);
          setDaydoList(data);
        });
  }

  // Update
  function updateDaydo() {
    const updatedDaydo = {
        content : "sunday todo #2"
    }

    fetch(`http://localhost:8080/api/daydo/1`, {
            method : 'PUT',
            headers : {
                'content-type' : 'application/json'
            },
            body : JSON.stringify(updatedDaydo)
        })
        .then((response) => response.json())
        .then((data) => {
          console.log("Updated ID: ", data);
        });
  }

  // Delete
  function deleteDaydo() {
    fetch(`http://localhost:8080/api/daydo/1`, {
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
      <p>TASK-daydo Client-Server API Test</p>

      <button onClick={createDaydo}>Create</button>
      <button onClick={fetchDaydo}>Read</button>
      <button onClick={updateDaydo}>Update</button>
      <button onClick={deleteDaydo}>Delete</button>
    </div>
  );
}

export default DaydoTest;
