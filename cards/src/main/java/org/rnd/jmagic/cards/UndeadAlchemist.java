package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Undead Alchemist")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class UndeadAlchemist extends Card
{
	public static final class UndeadAlchemistAbility0 extends StaticAbility
	{
		public static final class StupidDamage extends DamageReplacementEffect
		{
			public StupidDamage(Game game)
			{
				super(game, "If a Zombie you control would deal combat damage to a player, instead that player puts that many cards from the top of his or her library into his or her graveyard.");
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				GameState state = context.game.actualState;
				GameObject thisObject = (GameObject)this.getStaticSourceObject(state);
				Player you = thisObject.getController(state);

				DamageAssignment.Batch ret = new DamageAssignment.Batch();

				for(DamageAssignment assign: damageAssignments)
				{
					// combat damage
					if(!assign.isCombatDamage)
						continue;

					// to a player
					Identified taker = state.get(assign.takerID);
					if(!taker.isPlayer())
						continue;

					// from a zombie
					GameObject source = state.get(assign.sourceID);
					if(!source.getSubTypes().contains(SubType.ZOMBIE))
						continue;

					// you control
					if(source.controllerID == you.ID)
						ret.add(assign);
				}

				return ret;
			}

			@Override
			public java.util.List<EventFactory> replace(DamageAssignment.Batch damageAssignments)
			{
				java.util.List<EventFactory> ret = new java.util.LinkedList<EventFactory>();
				ret.add(millCards(PlayerByID.instance(damageAssignments.iterator().next().takerID), damageAssignments.size(), "That player puts that many cards from the top of his or her library into his or her graveyard."));

				damageAssignments.clear();

				return ret;
			}

		}

		public UndeadAlchemistAbility0(GameState state)
		{
			super(state, "If a Zombie you control would deal combat damage to a player, instead that player puts that many cards from the top of his or her library into his or her graveyard.");

			DamageReplacementEffect replacement = new StupidDamage(state.game);

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public static final class UndeadAlchemistAbility1 extends EventTriggeredAbility
	{
		public UndeadAlchemistAbility1(GameState state)
		{
			super(state, "Whenever a creature card is put into an opponent's graveyard from his or her library, exile that card and put a 2/2 black Zombie creature token onto the battlefield.");

			// 'technically' this will match a card going to an opponents
			// graveyard from a different opponents library, but i'm making the
			// assumption that that will never happen.
			SetGenerator opponents = OpponentsOf.instance(You.instance());
			this.addPattern(new SimpleZoneChangePattern(LibraryOf.instance(opponents), GraveyardOf.instance(opponents), HasType.instance(Type.CREATURE), false));

			this.addEffect(exile(NewObjectOf.instance(TriggerZoneChange.instance(This.instance())), "Exile that card."));

			CreateTokensFactory factory = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Zombie creature token onto the battlefield.");
			factory.setColors(Color.BLACK);
			factory.setSubTypes(SubType.ZOMBIE);
			this.addEffect(factory.getEventFactory());
		}
	}

	public UndeadAlchemist(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// If a Zombie you control would deal combat damage to a player, instead
		// that player puts that many cards from the top of his or her library
		// into his or her graveyard.
		this.addAbility(new UndeadAlchemistAbility0(state));

		// Whenever a creature card is put into an opponent's graveyard from his
		// or her library, exile that card and put a 2/2 black Zombie creature
		// token onto the battlefield.
		this.addAbility(new UndeadAlchemistAbility1(state));
	}
}
