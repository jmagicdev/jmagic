package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Geist Snatch")
@Types({Type.INSTANT})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class GeistSnatch extends Card
{
	public GeistSnatch(GameState state)
	{
		super(state);

		// Counter target creature spell. Put a 1/1 blue Spirit creature token
		// with flying onto the battlefield.
		SetGenerator creatureSpells = Intersect.instance(Spells.instance(), HasType.instance(Type.CREATURE));
		SetGenerator target = targetedBy(this.addTarget(creatureSpells, "target creature spell"));
		this.addEffect(counter(target, "Counter target creature spell."));

		CreateTokensFactory makeSpirit = new CreateTokensFactory(1, 1, 1, "Put a 1/1 blue Spirit creature token with flying onto the battlefield");
		makeSpirit.setColors(Color.BLUE);
		makeSpirit.setSubTypes(SubType.SPIRIT);
		makeSpirit.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
		this.addEffect(makeSpirit.getEventFactory());
	}
}
