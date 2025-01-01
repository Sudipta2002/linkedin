import React, { FormEvent, useState } from 'react'
import Layout from '../components/AuthenticationLayout/AuthenticationLayout'
import classes from "./Signup.module.scss";
import { Box } from '../components/Box/Box';
import { Input } from '../../../components/Input/Input';
import { Button } from '../components/Button/Button';
import { Seperator } from '../components/Seperator/Seperator';
import { Link, useNavigate } from 'react-router-dom';
import { useAuthentication } from '../context/AuthenticationContextProvider';
export default function Signup() {
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const { signup } = useAuthentication();
  const navigate = useNavigate();
  const doSignup = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    const email = e.currentTarget.email.value;
    const password = e.currentTarget.password.value;
    try {
      await signup(email, password);
      navigate("/");
    } catch (error) {
      if (error instanceof Error) {
        setErrorMessage(error.message);
      } else {
        setErrorMessage("An unknown error occurred.");
      }
    } finally {
      setIsLoading(false);
    }
  };
  return (
    <div className={classes.root}>
    <Box>
      <h1>Sign up</h1>
      <p>Make the most of your professional life.</p>
      <form onSubmit={doSignup}>
        <Input type="email" id="email" label="Email" onFocus={() => setErrorMessage("")} />
        <Input
          label="Password"
          type="password"
          id="password"
          onFocus={() => setErrorMessage("")}
        />
        {errorMessage && <p className={classes.error}>{errorMessage}</p>}
        <p className={classes.disclaimer}>
          By clicking Agree & Join or Continue, you agree to LinkedIn's{" "}
          <a href="">User Agreement</a>, <a href="">Privacy Policy</a>, and{" "}
          <a href="">Cookie Policy</a>.
        </p>
        <Button disabled={isLoading} type="submit">
          Agree & Join
        </Button>
      </form>
      <Seperator>Or</Seperator>
      <div className={classes.register}>
        Already on LinkedIn? <Link to="/authentication/login">Sign in</Link>
      </div>
    </Box>
  </div>
  )
}
