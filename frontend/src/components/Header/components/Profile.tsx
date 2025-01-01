import { Dispatch, SetStateAction, useEffect, useRef } from "react";
import { Link, useNavigate } from "react-router-dom";

import classes from "./Profile.module.scss";
import { useAuthentication } from "../../../features/authentication/context/AuthenticationContextProvider";
import { Button } from "../../../features/authentication/components/Button/Button";
interface IProfileProps {
    showProfileMenu: boolean;
    setShowNavigationMenu: Dispatch<SetStateAction<boolean>>;
    setShowProfileMenu: Dispatch<SetStateAction<boolean>>;
  }
const Profile = ({showProfileMenu,
    setShowProfileMenu,
    setShowNavigationMenu}:IProfileProps) => {
        const { logout, user } = useAuthentication();
        const ref = useRef<HTMLDivElement>(null);
        const navigate  = useNavigate();
        return (
        <div className={classes.root} ref={ref}>
        <button
            className={classes.toggle}
            onClick={() => {
            setShowProfileMenu((prev) => !prev);
            if (window.innerWidth <= 1080) {
                setShowNavigationMenu(false);
            }
            }}
        >
            <img className={classes.avatar} src={user?.profilePicture || "/avatar.svg"} alt="" />
            <div className={classes.name}>
            <div>{user?.firstName + " " + user?.lastName?.charAt(0) + "."}</div>
            </div>
        </button>

        {showProfileMenu ? (
            <div className={classes.menu}>
            <div className={classes.content}>
                <img
                className={`${classes.left} ${classes.avatar}`}
                src={user?.profilePicture || "/avatar.svg"}
                alt=""
                />
                <div className={classes.right}>
                <div className={classes.name}>{user?.firstName + " " + user?.lastName}</div>
                <div className={classes.title}>{user?.position + " at " + user?.company}</div>
                </div>
            </div>
            <div className={classes.links}>
                <Button
                size="small"
                className={classes.button}
                outline
                onClick={() => {
                    setShowProfileMenu(false);
                    navigate("/profile/" + user?.id);
                }}
                >
                View Profile
                </Button>

                <Link to="/settings" onClick={() => setShowProfileMenu(false)}>
                Settings & Privacy
                </Link>
                <Link
                to="/logout"
                onClick={(e) => {
                    e.preventDefault();
                    logout();
                }}
                >
                Sign Out
                </Link>
            </div>
            </div>
        ) : null}
        </div>
  )
}

export default Profile