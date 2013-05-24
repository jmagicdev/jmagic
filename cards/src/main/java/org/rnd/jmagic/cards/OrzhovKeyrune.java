package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Orzhov Keyrune")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class OrzhovKeyrune extends Card
{
	public static final class OrzhovKeyruneAbility1 extends ActivatedAbility
	{
		public OrzhovKeyruneAbility1(GameState state)
		{
			super(state, "(W)(B): Orzhov Keyrune becomes a 1/4 white and black Thrull artifact creature with lifelink until end of turn.");
			this.setManaCost(new ManaPool("(W)(B)"));

			Animator animate = new Animator(ABILITY_SOURCE_OF_THIS, 1, 4);
			animate.addColor(Color.WHITE);
			animate.addColor(Color.BLACK);
			animate.addSubType(SubType.THRULL);
			animate.addType(Type.ARTIFACT);
			animate.addAbility(org.rnd.jmagic.abilities.keywords.Lifelink.class);
			this.addEffect(createFloatingEffect("Orzhov Keyrune becomes a 1/4 white and black Thrull artifact creature with lifelink until end of turn.", animate.getParts()));

		}
	}

	public OrzhovKeyrune(GameState state)
	{
		super(state);

		// (T): Add (W) or (B) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(WB)"));

		// (W)(B): Orzhov Keyrune becomes a 1/4 white and black Thrull artifact
		// creature with lifelink until end of turn.
		this.addAbility(new OrzhovKeyruneAbility1(state));
	}
}
