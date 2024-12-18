import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.scss'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import {Feed} from './features/feed/pages/Feed'
import Login from './features/authentication/Login/Login'
import Signup from './features/authentication/Signup/Signup'
import ResetPassword from './features/authentication/ResetPassword/ResetPassword'
import VerifyEmail from './features/authentication/VerifyEmail/VerifyEmail'
import AuthenticationContextProvider from './features/authentication/context/AuthenticationContextProvider'

const router  = createBrowserRouter([

  {
    element: <AuthenticationContextProvider/>,
    children: [
      {
        path: '/',
        element:<Feed/>,
       },
       {
        path: '/login',
        element: <Login/>,
       },
       {
        path: '/signup',
        element: <Signup/>,
       },
       {
        path: '/request-password-reset',
        element: <ResetPassword/>,
       },
       {
        path: '/verify-email',
        element: <VerifyEmail/>,
       },
    ]
  }
   
]);

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RouterProvider router={router}/>
  </StrictMode>,
)
