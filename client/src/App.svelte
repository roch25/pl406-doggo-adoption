<script>
	import { Router, Route, Link } from "svelte-routing";
	import Home from './components/Home.svelte'
	import Footer from './components/Footer.svelte'
</script>

<main>
  <Router>
 
  <nav class="navbar navbar-inverse navb">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#">♡ Doggo ♡</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
        <li><a href="#">Adopt</a></li>
        <li><a href="#">Foster</a></li>
        <li><a href="#">Volunteer</a></li>
        <li><a href="#">Events</a></li>
        <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">Help<span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">Pet food</a></li>
            <li><a href="#">Vaccinaton Center</a></li>
            
          </ul>
        </li>
      </ul>
    
    </div>
  </div>
</nav>
    <!-- add routes -->
    <Route path="/"><Home /></Route>
  </Router>
	<Footer/>
</main>

<style>
	main {
		text-align: center;
		margin: 0 auto;
	}

	@media (min-width: 640px) {
		
	}


.navb{
	background-color:#302121;
}

</style>