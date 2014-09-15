package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gild")
@Types({Type.SORCERY})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class Gild extends Card
{
	public static final class Glitter extends ActivatedAbility
	{
		public Glitter(GameState state)
		{
			super(state, "Sacrifice this artifact: Add one mana of any color to your mana pool.");
			this.addCost(sacrificeThis("this artifact"));
			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG"));
		}
	}

	public Gild(GameState state)
	{
		super(state);

		// Exile target creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(exile(target, "Exile target creature."));

		// Put a colorless artifact token named Gold onto the battlefield.
		CreateTokensFactory gold = new CreateTokensFactory(numberGenerator(1), "Put a colorless artifact token named Gold onto the battlefield.");
		gold.setArtifact();
		gold.setName("Gold");
		EventFactory makeToken = gold.getEventFactory();
		this.addEffect(makeToken);

		// It has
		// "Sacrifice this artifact: Add one mana of any color to your mana pool."
		SetGenerator thatToken = NewObjectOf.instance(EffectResult.instance(makeToken));
		this.addEffect(addAbilityUntilEndOfTurn(thatToken, Glitter.class, "It has \"Sacrifice this artifact: Add one mana of any color to your mana pool.\""));
	}
}
