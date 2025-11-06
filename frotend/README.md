# Frontend: GitHub Org Snapshot (React + Vite)

This is a React application built with Vite that provides a user interface for displaying GitHub organization repositories.

The application allows users to enter an organization name, select sorting options, set a limit, and then fetches the data from a backend API.

## How to Run

1.  **Navigate to the frontend directory:**
    ```bash
    cd frontend
    ```

2.  **Install dependencies:**
    ```bash
    npm install
    ```

3.  **Run the dev server:**
    ```bash
    npm run dev
    ```

4.  Open the application in your browser. By default, it will be available at `http://localhost:5173`.

## Backend URL Configuration

By default, the frontend application expects the backend server to be running at `http://localhost:8080`.

If your backend is running on a different port or host (e.g., `http://localhost:3001` or a production URL), you must specify this address:

1.  In the root of the `/frontend` and find file named `.env`

2.  Replace the URL with your backend's correct address:

    ```bash
    VITE_API_BASE_URL="http://localhost:8080"
    ```

    **Example for a different port:**
    ```bash
    VITE_API_BASE_URL="http://localhost:3001"
    ```

> **Note:** After creating or modifying the `.env` file, you must **restart** your frontend development server (e.g., `npm run dev`) for the new variable to be loaded.