package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Brain Weevil")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BrainWeevil extends Card
{
	public static final class BrainWeevilAbility1 extends ActivatedAbility
	{
		public BrainWeevilAbility1(GameState state)
		{
			super(state, "Sacrifice Brain Weevil: Target player discards two cards. Activate this ability only any time you could cast a sorcery.");
			this.addCost(sacrificeThis("Brain Weevil"));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(discardCards(target, 2, "Target player discards two cards."));

			this.activateOnlyAtSorcerySpeed();
		}
	}

	public BrainWeevil(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Intimidate (This creature can't be blocked except by artifact
		// creatures and/or creatures that share a color with it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Intimidate(state));

		// Sacrifice Brain Weevil: Target player discards two cards. Activate
		// this ability only any time you could cast a sorcery.
		this.addAbility(new BrainWeevilAbility1(state));
	}
}
