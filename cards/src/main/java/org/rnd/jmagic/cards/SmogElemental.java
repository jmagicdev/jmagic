package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Smog Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4BB")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class SmogElemental extends Card
{
	public static final class SmogElementalAbility1 extends StaticAbility
	{
		public SmogElementalAbility1(GameState state)
		{
			super(state, "Creatures with flying your opponents control get -1/-1.");
			SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator what = Intersect.instance(CreaturePermanents.instance(), hasFlying, ControlledBy.instance(OpponentsOf.instance(You.instance())));
			this.addEffectPart(modifyPowerAndToughness(what, -1, -1));
		}
	}

	public SmogElemental(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Creatures with flying your opponents control get -1/-1.
		this.addAbility(new SmogElementalAbility1(state));
	}
}
