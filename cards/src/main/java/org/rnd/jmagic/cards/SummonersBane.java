package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Summoner's Bane")
@Types({Type.INSTANT})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class SummonersBane extends Card
{
	public SummonersBane(GameState state)
	{
		super(state);

		SetGenerator creatureSpells = Intersect.instance(Spells.instance(), HasType.instance(Type.CREATURE));
		Target target = this.addTarget(creatureSpells, "target creature spell");

		this.addEffect(counter(targetedBy(target), "Counter target creature spell."));

		// Put a 2/2 blue Illusion creature token onto the battlefield.
		CreateTokensFactory token = new CreateTokensFactory(1, 2, 2, "Put a 2/2 blue Illusion creature token onto the battlefield.");
		token.setColors(Color.BLUE);
		token.setSubTypes(SubType.ILLUSION);
		this.addEffect(token.getEventFactory());
	}
}
