FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

FROM node:20-alpine AS release
WORKDIR /app
COPY --from=build /app/dist ./dist
COPY --from=build /app/package.json ./
RUN npm ci --omit=dev
EXPOSE 3000
CMD ["node", "dist/module.mjs"]
