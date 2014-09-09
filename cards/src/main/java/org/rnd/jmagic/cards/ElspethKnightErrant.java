package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Elspeth, Knight-Errant")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.ELSPETH})
@ManaCost("2WW")
@ColorIdentity({Color.WHITE})
public final class ElspethKnightErrant extends Card
{
	public static final class MakeToken extends LoyaltyAbility
	{
		public MakeToken(GameState state)
		{
			super(state, +1, "Put a 1/1 white Soldier creature token onto the battlefield.");
			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Soldier creature token onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.SOLDIER);
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class Bless extends LoyaltyAbility
	{
		public Bless(GameState state)
		{
			super(state, +1, "Target creature gets +3/+3 and gains flying until end of turn.");
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(targetedBy(target), +3, +3, "Target creature gets +3/+3 and gains flying until end of turn.", org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public static final class AntiObliterate extends LoyaltyAbility
	{
		public static final class NotQuiteAsDeepAsBefore extends StaticAbility
		{
			public NotQuiteAsDeepAsBefore(GameState state)
			{
				super(state, "Artifacts, creatures, enchantments, and lands you control have indestructible.");

				SetGenerator stuff = Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance(), EnchantmentPermanents.instance(), LandPermanents.instance());
				this.addEffectPart(addAbilityToObject(Intersect.instance(stuff, ControlledBy.instance(You.instance())), org.rnd.jmagic.abilities.keywords.Indestructible.class));
			}
		}

		public AntiObliterate(GameState state)
		{
			super(state, -8, "You get an emblem with \"Artifacts, creatures, enchantments, and lands you control have indestructible.\"");

			EventFactory makeEmblem = new EventFactory(EventType.CREATE_EMBLEM, "You get an emblem with \"Artifacts, creatures, enchantments, and lands you control have indestructible.\"");
			makeEmblem.parameters.put(EventType.Parameter.CAUSE, This.instance());
			makeEmblem.parameters.put(EventType.Parameter.ABILITY, Identity.instance(NotQuiteAsDeepAsBefore.class));
			makeEmblem.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			this.addEffect(makeEmblem);
		}
	}

	public ElspethKnightErrant(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(4);

		// +1: Put a 1/1 white Soldier creature token onto the battlefield.
		this.addAbility(new MakeToken(state));

		// +1: Target creature gets +3/+3 and gains flying until end of turn.
		this.addAbility(new Bless(state));

		// -8: For the rest of the game, artifacts, creatures, enchantments, and
		// lands you control are indestructible.
		this.addAbility(new AntiObliterate(state));
	}
}
