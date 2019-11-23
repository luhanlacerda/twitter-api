import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

const routes = [
  {
    path: '/users',
    name: 'users',
    component: () => import('@/components/users/DataGrid'),
    meta: {
      title: 'Users'
    },
    icon: 'devices',
    items: ['new_usuarios', 'view_feed']
  },
  {
    path: '/users/new',
    name: 'new_usuarios',
    icon: 'add_circle',
    component: () => import('@/components/users/FormNew'),
    meta: {
      title: 'Novo Usuário'
    }
  },
  {
    path: '/tweeters/feed/:id',
    name: 'view_feed',
    component: () => import('@/components/feed/DataGrid'),
    meta: {
      title: 'Tweeters'
    },
    icon: 'devices',
    items: ['new_tweeters', 'edit_tweeters']
  },
  {
    path: '/tweeters',
    name: 'tweeters',
    component: () => import('@/components/tweeters/DataGrid'),
    meta: {
      title: 'Tweeters'
    },
    icon: 'devices',
    items: ['new_tweeters', 'edit_tweeters']
  },
  {
    path: '/tweeters/new',
    name: 'new_tweeters',
    icon: 'add_circle',
    component: () => import('@/components/tweeters/FormNew'),
    meta: {
      title: 'Novo Tweet'
    }
  },
  {
    path: '/tweeters/:id/edit',
    name: 'edit_tweeters',
    icon: 'create',
    component: () => import('@/components/tweeters/FormEdit'),
    props: true,
    meta: {
      title: 'Editar Tweet'
    }
  },
  {
    path: '/',
    name: ' users_root',
    component: () => import('@/components/users/DataGrid'),
    meta: {
      title: 'Users'
    },
    icon: 'devices'
  },
  {
    path: '*',
    name: 'not_found',
    icon: '',
    component: () => import('@/components/PageNotFound'),
    meta: {
      title: '404 - Not found'
    }
  } // esse deve ficar sempre por ultimo!!
];

const router = new Router({
  mode: 'hash',
  routes
});

// This callback runs before every route change, including on page load.
router.beforeEach((to, _, next) => {
  // This goes through the matched routes from last to first, finding the closest route with a title.
  // eg. if we have /some/deep/nested/route and /some, /deep, and /nested have titles, nested's will be chosen.
  const nearestWithTitle = to.matched
    .slice()
    .reverse()
    .find(r => r.meta && r.meta.title);

  // Find the nearest route element with meta tags.
  const nearestWithMeta = to.matched
    .slice()
    .reverse()
    .find(r => r.meta && r.meta.metaTags);

  // If a route with a title was found, set the document (page) title to that value.
  if (nearestWithTitle) document.title = nearestWithTitle.meta.title;

  // Remove any stale meta tags from the document using the key attribute we set below.
  Array.from(document.querySelectorAll('[data-vue-router-controlled]')).map(
    el => el.parentNode.removeChild(el)
  );

  // Skip rendering meta tags if there are none.
  if (!nearestWithMeta) return next();

  // Turn the meta tag definitions into actual elements in the head.
  nearestWithMeta.meta.metaTags
    .map(tagDef => {
      const tag = document.createElement('meta');

      Object.keys(tagDef).forEach(key => {
        tag.setAttribute(key, tagDef[key]);
      });

      // We use this to track which meta tags we create, so we don't interfere with other ones.
      tag.setAttribute('data-vue-router-controlled', '');

      return tag;
    })
    // add_circle the meta tags to the document head.
    .forEach(tag => document.head.appendChild(tag));

  next();
});

export default router;
export { routes };
