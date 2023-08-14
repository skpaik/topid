const runtimeCaching = require("next-pwa/cache");
const nextPwa = require("next-pwa");

const withPWA = nextPwa({
    //dest: "public",
    scope: '/',
    //sw: 'service-worker.js',
    register: true,
    skipWaiting: true,
    runtimeCaching,
    buildExcludes: [/middleware-manifest.json$/],
    disable: process.env.NODE_ENV !== "development"
});

const nextConfig = withPWA({
    reactStrictMode: true,
    i18n: {
        locales: ["en"],
        defaultLocale: "en",
    }
});
module.exports = nextConfig;