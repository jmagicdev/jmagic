package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Vraska the Unseen")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.VRASKA})
@ManaCost("3BG")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class VraskatheUnseen extends Card
{
	public static final class VraskatheUnseenAbility0 extends LoyaltyAbility
	{
		public static final class DelayedEnd extends UntilNextTurn.EventAndBeginTurnTracker
		{
			public DelayedEnd()
			{
				super(EventType.CREATE_DELAYED_TRIGGER);
			}
		}

		public VraskatheUnseenAbility0(GameState state)
		{
			super(state, +1, "Until your next turn, whenever a creature deals combat damage to Vraska the Unseen, destroy that creature.");

			DamagePattern pattern = new SimpleDamagePattern(null, delayedTriggerContext(ABILITY_SOURCE_OF_THIS), true);
			EventFactory destroy = destroy(SourceOfDamage.instance(TriggerDamage.instance(This.instance())), "Destroy that creature.");
			SetGenerator expires = Not.instance(Intersect.instance(delayedTriggerContext(This.instance()), UntilNextTurn.instance(DelayedEnd.class)));
			state.ensureTracker(new DelayedEnd());

			EventFactory trigger = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Until your next turn, whenever a creature deals combat damage to Vraska the Unseen, destroy that creature.");
			trigger.parameters.put(EventType.Parameter.CAUSE, This.instance());
			trigger.parameters.put(EventType.Parameter.DAMAGE, Identity.instance(pattern));
			trigger.parameters.put(EventType.Parameter.EFFECT, Identity.instance(destroy));
			trigger.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(expires));
			this.addEffect(trigger);
		}
	}

	public static final class VraskatheUnseenAbility1 extends LoyaltyAbility
	{
		public VraskatheUnseenAbility1(GameState state)
		{
			super(state, -3, "Destroy target nonland permanent.");

			SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND)), "target nonland permanent"));
			this.addEffect(destroy(target, "Destroy target nonland permanent."));
		}
	}

	public static final class VraskatheUnseenAbility2 extends LoyaltyAbility
	{
		public static final class PlayerDeathtouch extends EventTriggeredAbility
		{
			public static final Game.LoseReason LOSE_REASON = new Game.LoseReason("VraskatheUnseenAssassinate");

			public PlayerDeathtouch(GameState state)
			{
				super(state, "Whenever this creature deals combat damage to a player, that player loses the game.");
				this.addPattern(whenThisDealsCombatDamageToAPlayer());

				EventFactory lose = new EventFactory(EventType.LOSE_GAME, "That player loses the game.");
				lose.parameters.put(EventType.Parameter.CAUSE, Identity.instance(LOSE_REASON));
				lose.parameters.put(EventType.Parameter.PLAYER, TakerOfDamage.instance(TriggerDamage.instance(This.instance())));
				this.addEffect(lose);
			}
		}

		public VraskatheUnseenAbility2(GameState state)
		{
			super(state, -7, "Put three 1/1 black Assassin creature tokens onto the battlefield with \"Whenever this creature deals combat damage to a player, that player loses the game.\"");

			CreateTokensFactory tokens = new CreateTokensFactory(3, 1, 1, "Put three 1/1 black Assassin creature tokens onto the battlefield with \"Whenever this creature deals combat damage to a player, that player loses the game.\"");
			tokens.setColors(Color.BLACK);
			tokens.setSubTypes(SubType.ASSASSIN);
			tokens.addAbility(PlayerDeathtouch.class);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public VraskatheUnseen(GameState state)
	{
		super(state);

		this.setPrintedLoyalty((5));

		// +1: Until your next turn, whenever a creature deals combat damage to
		// Vraska the Unseen, destroy that creature.
		this.addAbility(new VraskatheUnseenAbility0(state));

		// -3: Destroy target nonland permanent.
		this.addAbility(new VraskatheUnseenAbility1(state));

		// -7: Put three 1/1 black Assassin creature tokens onto the battlefield
		// with
		// "Whenever this creature deals combat damage to a player, that player loses the game."
		this.addAbility(new VraskatheUnseenAbility2(state));
	}
}
