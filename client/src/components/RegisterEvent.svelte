<script>
  import Banner from "../utils/Banner.svelte";

  export let title = "Register for Event · Dog Adoption and Care";

  let message = "Register";
  let imgSrc = "images/register.jpg";

  const URL = `/api/AdoptionCampAug2021/`;

  let camp = {

  firstName:'',
  lastName:'',
  contact:''
  };

 
let addParticipant = async(event) =>{
  event.preventDefault()

    try {
      const response = await fetch(URL, {
        method: "post",
        headers: {
           'Content-Type': 'application/json; charset=utf-8',
            'Accept': 'application/json'
        },
         body: JSON.stringify(camp)
      });

      const createdParticipant= await response.json()
      console.log(createdParticipant)

    } catch (err) {
      console.log(err);
    }

  };

</script>

<svelte:head>
  <title>{title}</title>
</svelte:head>


<div>
  <Banner {message} {imgSrc} />

    <div class="cotainer">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-body">
                        <form name="my-form" method="post">
                            <div class="form-group row">
                                <label for="firstname" class="col-md-4 col-form-label text-md-right">First Name</label>
                                <div class="col-md-6">
                                    <input type="text" 
                                    	id="full_name" 
                                    	class="form-control" 
                                    	name="firstname"
                                    	bind:value={camp.firstName}
                                    >
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="lastname" class="col-md-4 col-form-label text-md-right">Last Name</label>
                                <div class="col-md-6">
                                    <input type="text" 
                                    	id="full_name" 
                                    	class="form-control" 
                                    	name="lastname"
                                    	bind:value={camp.lastName}
                                    >
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="contact" class="col-md-4 col-form-label text-md-right">Contact No.</label>
                                <div class="col-md-6">
                                  	<input type="text" 
                                  		id="full_name" 
                                  		class="form-control" 
                                  		name="contact"
                                  		bind:value={camp.contact}
                                  	>
                                </div>
                            </div>

                            <button
                              type="submit"
                              on:click|preventDefault = {addParticipant}
                              class="btn btn-primary" >
                              Post
                            </button>
                        </form>
                   </div>
                </div>
            </div>
        </div>
    </div>
  
</div>