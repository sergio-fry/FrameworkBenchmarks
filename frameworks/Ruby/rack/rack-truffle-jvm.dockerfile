FROM ghcr.io/sergio-fry/truffleruby-jvm:v24.2.0

ENV RUBY_YJIT_ENABLE=1
ENV RUBY_MN_THREADS=1

# Use Jemalloc
RUN apt-get update && \
    apt-get install -y --no-install-recommends libjemalloc2
ENV LD_PRELOAD=libjemalloc.so.2

RUN apt-get install -y --no-install-recommends libpq-dev

WORKDIR /rack

COPY Gemfile ./

RUN bundle config set with 'puma'
RUN bundle install --jobs=8

COPY . .

EXPOSE 8080

ENV RUBY_PLATFORM=java

CMD config/java_tune.sh
CMD bundle exec puma -C config/puma.rb -b tcp://0.0.0.0:8080 -e production

