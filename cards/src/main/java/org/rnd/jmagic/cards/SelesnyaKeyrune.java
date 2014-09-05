package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Selesnya Keyrune")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class SelesnyaKeyrune extends Card
{
	public static final class SelesnyaKeyruneAbility1 extends ActivatedAbility
	{
		public SelesnyaKeyruneAbility1(GameState state)
		{
			super(state, "(G)(W): Selesnya Keyrune becomes a 3/3 green and white Wolf artifact creature until end of turn.");
			this.setManaCost(new ManaPool("(G)(W)"));

			Animator animate = new Animator(ABILITY_SOURCE_OF_THIS, 3, 3);
			animate.addColor(Color.GREEN);
			animate.addColor(Color.WHITE);
			animate.addSubType(SubType.WOLF);
			animate.addType(Type.ARTIFACT);
			this.addEffect(createFloatingEffect("Selesnya Keyrune becomes a 3/3 green and white Wolf artifact creature until end of turn.", animate.getParts()));

		}
	}

	public SelesnyaKeyrune(GameState state)
	{
		super(state);

		// (T): Add (G) or (W) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(GW)"));

		// (G)(W): Selesnya Keyrune becomes a 3/3 green and white Wolf artifact
		// creature until end of turn.
		this.addAbility(new SelesnyaKeyruneAbility1(state));
	}
}
