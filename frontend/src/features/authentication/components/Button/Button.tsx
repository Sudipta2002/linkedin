import { ButtonHTMLAttributes } from "react";
import classes from "./Button.module.scss";
type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  outline?: boolean;
  size?: "small"|"medium"|"large";
};
export function Button({ outline,size, children, ...others }: ButtonProps) {
  return (
    <button {...others} className={`${classes.button} ${classes[size || "large"]}  ${outline ? classes.outline : ""}`}>
      {children}
    </button>
  );
}