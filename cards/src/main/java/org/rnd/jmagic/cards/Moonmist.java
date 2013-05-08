package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Moonmist")
@Types({Type.INSTANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Moonmist extends Card
{
	public Moonmist(GameState state)
	{
		super(state);

		SetGenerator humans = Intersect.instance(Permanents.instance(), HasSubType.instance(SubType.HUMAN));
		this.addEffect(transform(humans, "Transform all Humans."));

		SetGenerator nonFurries = RelativeComplement.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.WOLF, SubType.WEREWOLF));
		org.rnd.jmagic.abilities.PreventCombatDamage prevent = new org.rnd.jmagic.abilities.PreventCombatDamage(this.game, nonFurries, "Prevent all combat damage that would be dealt this turn by creatures other than Werewolves and Wolves.");
		this.addEffect(createFloatingReplacement(prevent, "Prevent all combat damage that would be dealt this turn by creatures other than Werewolves and Wolves."));
	}
}
