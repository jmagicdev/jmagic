package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Golgari Keyrune")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class GolgariKeyrune extends Card
{
	public static final class GolgariKeyruneAbility1 extends ActivatedAbility
	{
		public GolgariKeyruneAbility1(GameState state)
		{
			super(state, "(B)(G): Golgari Keyrune becomes a 2/2 black and green Insect artifact creature with deathtouch until end of turn.");
			this.setManaCost(new ManaPool("(B)(G)"));

			Animator animate = new Animator(ABILITY_SOURCE_OF_THIS, 2, 2);
			animate.addColor(Color.BLACK);
			animate.addColor(Color.GREEN);
			animate.addSubType(SubType.INSECT);
			animate.addType(Type.ARTIFACT);
			animate.addAbility(org.rnd.jmagic.abilities.keywords.Deathtouch.class);
			this.addEffect(createFloatingEffect("Golgari Keyrune becomes a 2/2 black and green Insect artifact creature with deathtouch until end of turn.", animate.getParts()));
		}
	}

	public GolgariKeyrune(GameState state)
	{
		super(state);

		// (T): Add (B) or (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(BG)"));

		// (B)(G): Golgari Keyrune becomes a 2/2 black and green Insect artifact
		// creature with deathtouch until end of turn.
		this.addAbility(new GolgariKeyruneAbility1(state));
	}
}
