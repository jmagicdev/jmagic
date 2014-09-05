package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Diluvian Primordial")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("5UU")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class DiluvianPrimordial extends Card
{
	public static final class DiluvianTarget extends Target
	{
		public DiluvianTarget(SetGenerator filter, String name)
		{
			super(filter, name);
			this.setNumber(0, null);
		}

		@Override
		public boolean checkSpecialRestrictions(GameState state, java.util.List<Target> choices)
		{
			if(choices.isEmpty())
				return true;

			java.util.List<Integer> owners = new java.util.LinkedList<Integer>();
			for(Target choice: choices)
			{
				int owner = state.<GameObject>get(choice.targetID).ownerID;
				if(owners.contains(owner))
					return false;
				owners.add(owner);
			}

			return true;
		}
	}

	public static final class DiluvianPrimordialAbility1 extends EventTriggeredAbility
	{
		public DiluvianPrimordialAbility1(GameState state)
		{
			super(state, "When Diluvian Primordial enters the battlefield, for each opponent, you may cast up to one target instant or sorcery card from that player's graveyard without paying its mana cost. If a card cast this way would be put into a graveyard this turn, exile it instead.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator inYards = InZone.instance(GraveyardOf.instance(OpponentsOf.instance(You.instance())));
			SetGenerator spellsInYards = Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), inYards);
			Target t = new DiluvianTarget(spellsInYards, "up to one target instant or sorcery card from each opponent's graveyard");
			this.addTarget(t);

			EventFactory cast = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "For each opponent, you may cast up to one target instant or sorcery card from that player's graveyard without paying its mana cost.");
			cast.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cast.parameters.put(EventType.Parameter.PLAYER, You.instance());
			cast.parameters.put(EventType.Parameter.OBJECT, targetedBy(t));
			this.addEffect(cast);

			SetGenerator castThisWay = EffectResult.instance(cast);

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(state.game, "If a card cast this way would be put into a graveyard this turn, exile it instead.");
			replacement.addPattern(new SimpleZoneChangePattern(null, GraveyardOf.instance(Players.instance()), castThisWay, true));
			replacement.changeDestination(ExileZone.instance());
			this.addEffect(createFloatingReplacement(replacement, "If a card cast this way would be put into a graveyard this turn, exile it instead."));
		}
	}

	public DiluvianPrimordial(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Diluvian Primordial enters the battlefield, for each opponent,
		// you may cast up to one target instant or sorcery card from that
		// player's graveyard without paying its mana cost. If a card cast this
		// way would be put into a graveyard this turn, exile it instead.
		this.addAbility(new DiluvianPrimordialAbility1(state));
	}
}
