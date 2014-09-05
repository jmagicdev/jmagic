package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Dimir Keyrune")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DimirKeyrune extends Card
{
	public static final class DimirKeyruneAbility1 extends ActivatedAbility
	{
		public DimirKeyruneAbility1(GameState state)
		{
			super(state, "(U)(B): Dimir Keyrune becomes a 2/2 blue and black Horror artifact creature until end of turn and can't be blocked this turn.");
			this.setManaCost(new ManaPool("(U)(B)"));

			Animator animate = new Animator(ABILITY_SOURCE_OF_THIS, 2, 2);
			animate.addColor(Color.BLUE);
			animate.addColor(Color.BLACK);
			animate.addSubType(SubType.HORROR);
			animate.addType(Type.ARTIFACT);

			ContinuousEffect.Part[] parts = animate.getParts();
			parts = java.util.Arrays.copyOf(parts, parts.length + 1);

			parts[parts.length - 1] = unblockable(ABILITY_SOURCE_OF_THIS);

			this.addEffect(createFloatingEffect("Dimir Keyrune becomes a 2/2 blue and black Horror artifact creature until end of turn and is unblockable this turn.", parts));
		}
	}

	public DimirKeyrune(GameState state)
	{
		super(state);

		// (T): Add (U) or (B) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(UB)"));

		// (U)(B): Dimir Keyrune becomes a 2/2 blue and black Horror artifact
		// creature until end of turn and is unblockable this turn.
		this.addAbility(new DimirKeyruneAbility1(state));
	}
}
