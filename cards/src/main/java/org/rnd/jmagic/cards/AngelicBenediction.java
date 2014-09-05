package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Angelic Benediction")
@Types({Type.ENCHANTMENT})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class AngelicBenediction extends Card
{
	public static final class AngelicBenedictionAbility1 extends org.rnd.jmagic.abilityTemplates.ExaltedBase
	{
		public AngelicBenedictionAbility1(GameState state)
		{
			super(state, "Whenever a creature you control attacks alone, you may tap target creature.");
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(youMay(tap(target, "Tap target creature"), "You may tap target creature."));
		}
	}

	public AngelicBenediction(GameState state)
	{
		super(state);

		// Exalted (Whenever a creature you control attacks alone, that creature
		// gets +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));

		// Whenever a creature you control attacks alone, you may tap target
		// creature.
		this.addAbility(new AngelicBenedictionAbility1(state));
	}
}
