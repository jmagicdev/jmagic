package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mobilization")
@Types({Type.ENCHANTMENT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class Mobilization extends Card
{
	public static final class VigilantGuard extends StaticAbility
	{
		public VigilantGuard(GameState state)
		{
			super(state, "Soldier creatures have vigilance.");

			this.addEffectPart(addAbilityToObject(Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.SOLDIER)), org.rnd.jmagic.abilities.keywords.Vigilance.class));
		}
	}

	public static final class SpawnSoldier extends ActivatedAbility
	{
		public SpawnSoldier(GameState state)
		{
			super(state, "(2)(W): Put a 1/1 white Soldier creature token onto the battlefield.");

			this.setManaCost(new ManaPool("2W"));

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Soldier creature token onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.SOLDIER);
			this.addEffect(token.getEventFactory());
		}
	}

	public Mobilization(GameState state)
	{
		super(state);

		this.addAbility(new VigilantGuard(state));
		this.addAbility(new SpawnSoldier(state));
	}
}
