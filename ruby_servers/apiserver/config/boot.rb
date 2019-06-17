ENV['BUNDLE_GEMFILE'] ||= File.expand_path('../../Gemfile', __FILE__)

require 'bundler/setup' # Set up gems listed in the Gemfile.

require 'rails/commands/server'

module Rails
  # Override default development
  # Server port
  class Server
    def default_options
      super.merge(Port: 3100)
    end
  end
end
