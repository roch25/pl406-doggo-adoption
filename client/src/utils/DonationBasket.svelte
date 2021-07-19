<script>
  import { onMount } from "svelte";
  import Product from "./Product.svelte";
  import { user } from "../stores/user";

  let currentUser = {};

  user.subscribe((n) => {
    if (n) currentUser = n;
  });

  const productTypes = [
    "flea",
    "food",
    "travel",
    "feed",
    "health",
    "scooper",
    "stain-removal",
  ];

  const URL = "/api/products";
  let productList = [];

  onMount(async () => {
    try {
      productList = await Promise.all([
        ...productTypes.map((type) => fetch(`${URL}/${type}`)),
      ]).then((res) =>
        Promise.all(res.map(async (product) => await product.json()))
      );
    } catch (err) {
      console.log(err);
    }
  });

  let donation = {
    amount: 0,
    items: [],
  };

  const add = function ({ price, link }) {
    donation.items.push(link);
    if (price.includes(",")) price = price.replace(/,/g, "");
    donation.amount += +price.substring(2);
    console.table(donation);
  };

  const remove = function ({ price, link }) {
    const index = donation.items.findIndex((item) => item == link);
    donation.items.splice(index, 1);
    if (price.includes(",")) price = price.replace(/,/g, "");
    donation.amount -= +price.substring(2);
    console.table(donation);
  };

  const removeAll = function ({ price, link }) {
    let initial = donation.items.length;
    donation.items = donation.items.filter((item) => item != link);
    if (price.includes(",")) price = price.replace(/,/g, "");
    let total = +price.substring(2) * (initial - donation.items.length);
    donation.amount -= total;
    console.table(donation);
  };

  let show = false;
</script>

<div class="mx-auto">
  {#each productList as products, index}
    <h2 class="bg-secondary-100 rounded py-2">{productTypes[index]}</h2>
    {#if products}
      <div class="column">
        {#each products.slice(0, 10) as product}
          <Product {product} {add} {remove} {removeAll} />
        {/each}
      </div>
      {#if show}
        <div class="column">
          {#each products.slice(10) as product}
            <Product {product} {add} {remove} {removeAll} />
          {/each}
        </div>
      {:else}
        <div
          class="bg-secondary-100 rounded py-2 text-center  pointer"
          on:click={() => {
            show = true;
          }}
        >
          Load more
        </div>
      {/if}
    {/if}
  {/each}
</div>

<div class="fab-container">
  <a
    class="fab-content pointer"
    href="/api/checkout/{donation.amount}"
    title="Make donation"
  >
    <span class="material-icons">
      <i class="fa fa-shopping-basket" aria-hidden="true" />
      {#if donation.amount}{donation.amount}{/if}
    </span>
  </a>
</div>

<style>
  .fab-container {
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    align-items: center;
    user-select: none;
    position: fixed;
    bottom: 30px;
    z-index: 999;
    right: 30px;
  }

  .fab-content .material-icons {
    font-size: 2em;
    opacity: 0.6;
  }

  .column {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  }

  @media (max-width: 1050px) {
    .column {
      grid-template-columns: 1fr 1fr 1fr 1fr;
    }
  }

  @media (max-width: 750px) {
    .column {
      grid-template-columns: 1fr 1fr;
    }
  }

  @media (max-width: 450px) {
    .column {
      grid-template-columns: 1fr;
    }
  }

  /* 

  @media (max-width: 800px) {
    .column {
      column-count: 5;
    }
  }

  @media (max-width: 1000px) {
    .column {
      column-count: 6;
    }
  } */
</style>
