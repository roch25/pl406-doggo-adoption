<script>
  import { user } from "../stores/user";
  import { openModal } from "../stores/store";
  import moment from "moment";

  export let tempPost;
  let showComments = false;
  let comment = "";
  let sendBtnVisible = comment != "";
  let currentUser = {};
  let disabled = false
  let postCommentClass = "input-group-text pointer"

  user.subscribe((updatedUser) => {
      if (updatedUser) currentUser = updatedUser;
  });

  const open = () => { showComments = true; };

  let interval;

  const checkAuth = (action) => {
    interval = setInterval(() => {
      currentUser.isLoggedIn && action()
    }, 3000);
  };

  const post = async () => {

    const response = await fetch('/api/posts/comment/', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
        'Accept': 'application/json',
        Authorization: currentUser.type + " " + currentUser.token,
      },
      referrerPolicy: 'no-referrer',
      body: JSON.stringify({
        comment,
        username: currentUser.username,
        postId: tempPost.id
      })
    })
    const c = await response.json()
    tempPost.comments.push({
      comment: c.comment,
      user: {
        username: c.username
      }
    })
    tempPost = tempPost;
    comment = "";
    sendBtnVisible = false
    enableComments();
    clearInterval(interval);
  };

  const disableComments = function() {
      disabled = true
      postCommentClass += " disabled"
     
  }

  const enableComments = function() {
      disabled = false
      postCommentClass = "input-group-text pointer"
  }

  const postComment = () => {
    disableComments()
    if (!currentUser.isLoggedIn) {
      openModal("login");
      checkAuth(post);
    } else {
      post();
    }
  };

  const handleChange = () => {
    sendBtnVisible = comment != "";
  };
</script>

<style>
  img {
    width: 400px;
    height: 300px;
    object-fit: cover;
  }

  .disabled {
    cursor: none;
    opacity: 0.6;
  }
</style>

<div class="card col-10 p-4 my-2">
  <div class="mb-4 text-left">
    <div class="media-body">
      <span class="mb-2">@ {tempPost.user.username}</span>
      <div class="text-muted small">
        {moment.utc(tempPost.createdDate).fromNow()}
      </div>
    </div>
  </div>

  {#if (tempPost.user.username == currentUser.username)}
     <i class="fa fa-trash pointer" aria-hidden="true"></i>
  {/if}

  {#if tempPost.imagePath}
    <img src={tempPost.imagePath} class=" img-fluid" alt="..." />
  {/if}
  <div class="card-body text-left">
    <p>{tempPost.caption}</p>
  </div>

  <div class="card-body text-left">
    {#each tempPost.tags as tag}
      <small class="badge badge-info mr-1 pointer"> {tag} </small>
    {/each}
  </div>

  <div class="card-footer text-left">
    {#each tempPost.comments.slice(0, 2) as comment}
      <div>
        <small>
          <strong>@{comment.user.username}</strong>
          <em>{comment.comment}</em>
          {#if (comment.user.username == currentUser.username)}
            <i class="fa fa-minus-square-o pointer" aria-hidden="true"></i>
          {/if}
        </small>
      </div>
    {/each}

    <small class="align-left pointer" on:click={open}>
      {#if tempPost.comments?.length - 2 > 0}
        <strong> {tempPost.comments?.length - 2 || 0} </strong>
        more comment(s)
      {/if}
    </small>

    {#if showComments}
      {#each tempPost.comments.slice(2) as comment}
        <div>
          <small>
              <strong>@{comment.user.username}</strong>
              <em>{comment.comment}</em>
              {#if (comment.user.username == currentUser.username)}
                <i class="fa fa-minus-square-o pointer" aria-hidden="true">d</i>
              {/if}
          </small>
        </div>
      {/each}
    {/if}
  </div>
  <div class=" mb-3">
    <form on:submit|preventDefault={postComment}>
      <div class="input-group">
        <div class="input-group-prepend">
          <span class="input-group-text" id="inputGroupPrepend3"><i
              class="far fa-comment" /></span>
        </div>
        <input
          type="text"
          class="form-control"
          bind:value={comment}
          on:input={handleChange}
          placeholder="Write a comment or a query"
          aria-describedby="inputGroupPrepend3"
          disabled={disabled} 
          required />

        {#if sendBtnVisible}
          <span
            class={postCommentClass}
            id="inputGroupPrepend3"
            on:click={postComment}><i class="fas fa-paper-plane" />
          </span>
        {/if}
      </div>
    </form>
  </div>
</div>
