package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Zameck Guildmage")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.ELF})
@ManaCost("GU")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class ZameckGuildmage extends Card
{
	public static final class ZameckGuildmageAbility0 extends ActivatedAbility
	{
		public ZameckGuildmageAbility0(GameState state)
		{
			super(state, "(G)(U): This turn, each creature you control enters the battlefield with an additional +1/+1 counter on it.");
			this.setManaCost(new ManaPool("(G)(U)"));

			SetGenerator creatures = HasType.instance(Type.CREATURE);

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, this.getName());
			replacement.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), creatures, You.instance(), false));

			SetGenerator zoneChange = ReplacedBy.instance(Identity.instance(replacement));

			EventFactory factory = new EventFactory(EventType.PUT_COUNTERS, this.getName());
			factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(zoneChange));
			factory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			factory.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(zoneChange));
			replacement.addEffect(factory);

			this.addEffect(createFloatingReplacement(replacement, "This turn, each creature you control enters the battlefield with an additional +1/+1 counter on it."));
		}
	}

	public static final class ZameckGuildmageAbility1 extends ActivatedAbility
	{
		public ZameckGuildmageAbility1(GameState state)
		{
			super(state, "(G)(U), Remove a +1/+1 counter from a creature you control: Draw a card.");
			this.setManaCost(new ManaPool("(G)(U)"));
			// Remove a +1/+1 counter from a creature you control

			EventFactory removeCounters = new EventFactory(EventType.REMOVE_COUNTERS_FROM_CHOICE, "Remove a +1/+1 counter from a creature you control");
			removeCounters.parameters.put(EventType.Parameter.CAUSE, This.instance());
			removeCounters.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			removeCounters.parameters.put(EventType.Parameter.OBJECT, CREATURES_YOU_CONTROL);
			removeCounters.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addCost(removeCounters);
			this.addEffect(drawACard());
		}
	}

	public ZameckGuildmage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (G)(U): This turn, each creature you control enters the battlefield
		// with an additional +1/+1 counter on it.
		this.addAbility(new ZameckGuildmageAbility0(state));

		// (G)(U), Remove a +1/+1 counter from a creature you control: Draw a
		// card.
		this.addAbility(new ZameckGuildmageAbility1(state));
	}
}
