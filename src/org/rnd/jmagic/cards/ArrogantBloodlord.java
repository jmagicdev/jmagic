package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Arrogant Bloodlord")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.KNIGHT})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class ArrogantBloodlord extends Card
{
	public static final class Arrogance extends EventTriggeredAbility
	{
		public Arrogance(GameState state)
		{
			super(state, "Whenever Arrogant Bloodlord blocks or becomes blocked by a creature with power 1 or less, destroy Arrogant Bloodlord at end of combat.");

			SetGenerator powerOneOrLess = HasPower.instance(Between.instance(null, 1));

			SimpleEventPattern blocks = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			blocks.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			blocks.put(EventType.Parameter.ATTACKER, powerOneOrLess);
			this.addPattern(blocks);

			SimpleEventPattern becomesBlocked = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			becomesBlocked.put(EventType.Parameter.OBJECT, powerOneOrLess);
			becomesBlocked.put(EventType.Parameter.ATTACKER, ABILITY_SOURCE_OF_THIS);
			this.addPattern(becomesBlocked);

			SimpleEventPattern atEndOfCombat = new SimpleEventPattern(EventType.BEGIN_STEP);
			atEndOfCombat.put(EventType.Parameter.STEP, EndOfCombatStepOf.instance(Players.instance()));

			EventFactory factory = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Destroy Arrogant Bloodlord at end of combat.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.EVENT, Identity.instance(atEndOfCombat));
			factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(destroy(ABILITY_SOURCE_OF_THIS, "Destroy Arrogant Bloodlord.")));
			this.addEffect(factory);
		}
	}

	public ArrogantBloodlord(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Whenever Arrogant Bloodlord blcks or becomes blocked by a creature
		// with power 1 or less, destroy Arrogant Bloodlord at end of combat.
		this.addAbility(new Arrogance(state));
	}
}
