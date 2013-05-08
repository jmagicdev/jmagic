package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Simic Keyrune")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class SimicKeyrune extends Card
{
	public static final class SimicKeyruneAbility1 extends ActivatedAbility
	{
		public SimicKeyruneAbility1(GameState state)
		{
			super(state, "(G)(U): Simic Keyrune becomes a 2/3 green and blue Crab artifact creature with hexproof until end of turn.");
			this.setManaCost(new ManaPool("(G)(U)"));

			Animator animate = new Animator(ABILITY_SOURCE_OF_THIS, 2, 3);
			animate.addColor(Color.GREEN);
			animate.addColor(Color.BLUE);
			animate.addSubType(SubType.CRAB);
			animate.addType(Type.ARTIFACT);
			animate.addAbility(org.rnd.jmagic.abilities.keywords.Hexproof.class);
			this.addEffect(createFloatingEffect("Simic Keyrune becomes a 2/3 green and blue Crab artifact creature with hexproof until end of turn.", animate.getParts()));
		}
	}

	public SimicKeyrune(GameState state)
	{
		super(state);

		// (T): Add (G) or (U) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(GU)"));

		// (G)(U): Simic Keyrune becomes a 2/3 green and blue Crab artifact
		// creature with hexproof until end of turn. (It can't be the target of
		// spells or abilities your opponents control.)
		this.addAbility(new SimicKeyruneAbility1(state));
	}
}
