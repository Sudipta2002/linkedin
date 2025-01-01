import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.scss'
import { createBrowserRouter, Navigate, RouterProvider } from 'react-router-dom'
import {Feed} from './features/feed/pages/Feed/Feed'
import Login from './features/authentication/Login/Login'
import Signup from './features/authentication/Signup/Signup'
import ResetPassword from './features/authentication/ResetPassword/ResetPassword'
import VerifyEmail from './features/authentication/VerifyEmail/VerifyEmail'
import AuthenticationContextProvider from './features/authentication/context/AuthenticationContextProvider'
import AuthenticationLayout from './features/authentication/components/AuthenticationLayout/AuthenticationLayout'
import ApplicationLayout from './components/ApplicationLayout/ApplicationLayout'

const router  = createBrowserRouter([

  {
    element: <AuthenticationContextProvider/>,
    children: [
      {
        path:'/',
        element:<ApplicationLayout/>,
        children:[
          {
            index: true,
            element:<Feed/>,
          },
          {
            // path: "posts/:id",
            // element: <PostPage />,
          },
          {
            path: "network",
            element: <div>Network</div>,
          },
          {
            path: "jobs",
            element: <div>Jobs</div>,
          },
        ],
      },
      {
        path: "/authentication",
        element: <AuthenticationLayout/>,
        children:[
           {
            path: 'login',
            element: <Login/>,
           },
           {
            path: 'signup',
            element: <Signup/>,
           },
           {
            path: 'request-password-reset',
            element: <ResetPassword/>,
           },
           {
            path: 'verify-email',
            element: <VerifyEmail/>,
           },
        ]
      },
      {
        path: '*',
        element: <Navigate to="/"/>,
      }
    ]
  }
   
]);

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RouterProvider router={router}/>
  </StrictMode>,
)
