package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ghor-Clan Rampager")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("2RG")
@ColorIdentity({Color.RED, Color.GREEN})
public final class GhorClanRampager extends Card
{
	public GhorClanRampager(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Bloodrush \u2014 (R)(G), Discard Ghor-Clan Rampager: Target attacking
		// creature gets +4/+4 and gains trample until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.Bloodrush(state, "(R)(G)", "Ghor-Clan Rampager", +4, +4, "Target attacking creature gets +4/+4 and gains trample until end of turn.", org.rnd.jmagic.abilities.keywords.Trample.class));
	}
}
