import { useState } from 'react'
import './App.css'
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";
function App() {
  const [organization, setOrganization] = useState("vercel");
  const [sort, setSort] = useState("stars");
  const [limit, setLimit] = useState(5);
  const [repositories, setRepositories] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isRepositoriesEmpty, setIsRepositoriesEmpty] = useState(true);
  const [error, setError] = useState(null);
  
  async function loadRepositories(){
    if (!organization) {
      setOrganization("vercel");
      return; 
    }
    try{
      setLoading(true);
      setError(null);
      const response = await fetch(
        `${API_BASE_URL}/api/org/${organization}/repos?limit=${limit}&sort=${sort}`
      );
      const data = await response.json();
      setRepositories(data); 
      setLoading(false);
      setIsRepositoriesEmpty(data.length === 0);
    }catch(e){
      console.log(e);
      setError(e.message); 
      setRepositories([]); 
      setIsRepositoriesEmpty(false); 
    }finally{
      setLoading(false);
    }
  }
  return (
    <>
    {loading && (
      <div className='loading'>
        <p>. . .</p>
      </div>
    )}
    <header>
      <h1>GitHub Org Snapshot</h1>
    </header>
    <div className='search'>
      <input type="text" value={organization} placeholder='Enter an organization name' onChange={(e) => setOrganization(e.target.value)}/>
      <p>sort by</p>
      <select value={sort} onChange={(e) => setSort(e.target.value)}>
        <option value="stars">Stars</option>
        <option value="updated">Updated</option>
      </select>
      <input type="number" value={limit} min="1" max="20" placeholder='limit 1–20' onChange={(e) => setLimit(e.target.value)}/>
      <button onClick={() => loadRepositories()}>Load</button>
    </div>
    <div className='content'>
    {error ? (
      <div className='error'>
        <p>Something went wrong: {error}</p>
      </div>
    ) : isRepositoriesEmpty ? (
      <div className='empty'>
        <p>Please enter an existing organization name and press 'Load'.</p>
      </div>
    ) : (
      <ul>
        {repositories.map(repository =>(
          <li key={repository.name}>
            <a href={repository.html_url}>{repository.name}</a>
            <p>stars: {repository.stargazers_count}</p>
            <p>forks: {repository.forks_count}</p>
            <p>language: {repository.language}</p>
            <p>updatedAt: {new Date(repository.updated_at).toLocaleDateString()}</p>
            <p>description: {repository.description}</p>
          </li>
        ))}
      </ul>
    )}
    </div>
    </>
  )
}

export default App
