<script>
  import { Link } from "svelte-routing";
  import { app, socials, tags } from "../stores/app"
</script>

<footer class="py-4">
  <div class="container">
    <div class="row">
      <div class="col-md-6 item text mb-2">
        <img src="images/icons/logo-w.png" alt="logo" class="w-25" />
        <h3 class="font-weight-bold container ">{ app.name }</h3>
        <p class="mb-0"> {app.sub} </p>
        <p class="mb-0"> {app.tag} </p>
      </div>
      <div class="col-sm-6 col-md-3 item contact">
        <address class="col-sm-12">
          <h5 class="font-weight-bold h5">Address</h5>
          <small class="d-block">{app.address.name}</small>
          <small class="d-block">{app.address.place}</small>
        </address>
        <div class="col-sm-12">
          <h5 class="font-weight-bold h5">Contact<h5>
          <ul class="px-0 list-unstyled">
            <li class="mb-1">
              <span><i class="fa fa-envelope mr-1" />
                <a href={`mailto:${app.contact.email}`}>{app.contact.email}</a>
              </span>
            </li>
            <li class="mb-1">
              <span><i class="fas fa-phone-alt mr-1" />
                <a href={`tel:${app.contact.phone}`}>{app.contact.phone}</a></span>
            </li>
          </ul>
        </div>
      </div>
      <div class="col-sm-6 col-md-3 item widget">
        <h5 class="font-weight-bold h5">Tags</h5>      
        {#each tags as tag}
        <Link class="d-inline px-0" to={tag}>
          <small class="p-2 d-inline-block mb-1 rounded text-decoration-none text-capitalize text-white">{tag}</small>
        </Link>        
        {/each}
      </div>

      <div class="col item social pt-3 mt-2 border-top border-secondary">
        {#each socials as social}
          <a href={social.link} class="rounded-circle d-inline-block mr-1 text-white shadow">
            <i class={social.icon} />
          </a>
        {/each}
      </div>
    </div>
    <small class="mt-2">Dog Adoption and Care · 2021</small>
  </div>
</footer>

<style>

  footer ul a {
    font-size: 14px;
    color: inherit;
    opacity: 0.6;
  }

  footer ul a:hover {
    opacity: 0.8;
  }

  .widget small {
    border: 1px solid rgb(116, 116, 116);
    color: #fff0f9;
    opacity: 0.6;
  }

  .widget small:hover {
    border: 1px solid rgb(156, 156, 156);
    opacity: 0.9;
  }

  span > i {
    font-size: smaller;
  }

  footer .item.social > a {
    width: 35px;
    height: 35px;
    line-height: 35px;
    background: rgba(255, 255, 255, 0.05);
  }

  footer .item.social > a:hover {
    background: rgba(255, 255, 255, 0.1);
  }

  @media (max-width: 767px) {
    footer .item:not(.social) {
      text-align: center;
      padding-bottom: 18px;
    }

    footer .item.text {
      margin-bottom: 20px;
    }
  }
  
  @media (max-width: 991px) {
    footer .item.social {
      text-align: center;
      margin-top: 20px;
    }
  }
</style>
