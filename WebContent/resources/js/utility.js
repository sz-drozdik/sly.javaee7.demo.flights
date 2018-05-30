hideOrShake = function(args, widget) {
	var succ = args && !args.validationFailed;
	if (succ) {
		widget.hide()
	} else {
		widget.jq.effect('shake', {
			times : 5
		}, 100);
	}
}

socketListener = function(message, channel, event) {
	socketFired([ {
		name : 'flightCode',
		value : message.flightCode
	}, {
		name : 'opCode',
		value : message.opCode
	} ])
}