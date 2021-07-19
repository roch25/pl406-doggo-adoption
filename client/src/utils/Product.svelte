<script>
  export let product;

  let { name, imgSrc, price, link } = product;

  link = `https://amazon.in${link}`;

  export let add;
  export let remove;
  export let removeAll;

  let count = 0;

  $: class1 = count
    ? "card m-2 py-4 px-2 pointer selected"
    : "card m-2 py-4 px-2 pointer";
</script>

<div class={class1}>
  <div class="h6 card-title">{name}</div>
  <img src={imgSrc} alt={name} class="mb-2" />
  <p id="price" class="badge badge-info text-md p-2 h5">{price}</p>
  <div
    class="controls bg-secondary-100 w-100 d-flex justify-content-around flex-reverse"
  >
    {#if count}
      <button
        on:click={() => {
          removeAll({ price, link });
          count = 0;
        }}><i class="fas fa-trash-alt" /></button
      >
      <button
        on:click={() => {
          remove({ price, link });
          count--;
        }}><i class="fas fa-minus" /></button
      >
      <span class="px-3"> {count} </span>
    {/if}
    <button
      on:click={() => {
        add({ price, link });
        count++;
      }}
    >
      <i class="fas fa-plus" /></button
    >
  </div>
</div>

<style>
  .selected {
    background: #d9dfd1c2;
  }

  .card {
    position: relative;
    overflow: hidden;
  }

  img {
    width: 80%;
  }

  #price {
    position: absolute;
    left: 0;
    top: 0;
  }

  .controls {
    position: absolute;
    bottom: 0;
  }
</style>
