import { useNavigate } from "react-router-dom";
import { Button } from "../../../authentication/components/Button/Button";
import LeftSidebar from "../../components/LeftSidebar/LeftSidebar";
import RightSidebar from "../../components/RightSidebar/RightSidebar";
import classes from "./Feed.module.scss";
import { useEffect, useState } from "react";
import { useAuthentication } from "../../../authentication/context/AuthenticationContextProvider";
import Post, { Posts } from "../../components/Post/Post";
import { Modal } from "../../components/Modal/Modal";
// import {useAuthentication} from "../authentication/contexts/AuthenticationContextProvider.tsx";
export function Feed() {
    const { user} = useAuthentication();
    const navigate = useNavigate();
    const [feedContent, setFeedContent] = useState<"all" | "connexions">("connexions");
    const [showPostingModal,setShowPostingModal] = useState(false);
    const [posts,setPosts] = useState<Posts[]>([]);
    const [error, setError] = useState("");


    useEffect(() => {
        const fetchPosts = async () => {
          try {
            const response = await fetch(import.meta.env.VITE_API_URL+
                "/api/v1/feed"+(feedContent==="connexions"?"":"/posts"),
                {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem("token")}`,
                    },
                }
            );
            
            if (!response.ok) {
              throw new Error("Failed to fetch posts");
            }
            const data = await response.json();
            setPosts(data);
            console.log(data[0]);

          } catch (error) {
            if(error instanceof Error) {
                setError(error.message);
            }else{
                setError("An unknown error occurred.");
            }
          }
        };
        fetchPosts();
      }, [feedContent]);

      const handlePost = async (content: string) => {
        try {
          const response = await fetch(import.meta.env.VITE_API_URL+"/api/v1/posts", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
            body: JSON.stringify({ content }),
          });
        }catch(e){
            console.log(e);
            
        }
      }

    return (
        <div className={classes.root}>
           
            <div className={classes.left}>
                <LeftSidebar/>
            </div>
            <div className={classes.center}>
            <div className={classes.posting}>
          <button
            onClick={() => {
              navigate(`/profile/${user?.id}`);
            }}
          >
            <img
              className={`${classes.top} ${classes.avatar}`}
              src={user?.profilePicture || "/avatar.svg"}
              alt=""
            />
          </button>
          <Button outline onClick={() => setShowPostingModal(true)}>
            Start a post
          </Button>
          <Modal
            title="Creating a post"
            onSubmit={handlePost}
            showModal={showPostingModal}
            setShowModal={setShowPostingModal}
          />
          </div>
          {error && <div className={classes.error}>{error}</div>}

          <div className={classes.header}>
          <button
            className={feedContent === "all" ? classes.active : ""}
            onClick={() => setFeedContent("all")}
          >
            All
          </button>
          <button
            className={feedContent === "connexions" ? classes.active : ""}
            onClick={() => setFeedContent("connexions")}
          >
            Feed
          </button>
        </div>
        <div className={classes.feed}>
          {posts.map((post) => (
            <Post key={post.id} post={post} setPosts={setPosts} />
          ))}
        </div>
      
            </div>
            <div className={classes.right}>
                <RightSidebar/>
            </div>
            
        </div>
    );
}