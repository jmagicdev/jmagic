package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Illusionist's Bracers")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@ColorIdentity({})
public final class IllusionistsBracers extends Card
{
	public static final class IllusionistsBracersAbility0 extends EventTriggeredAbility
	{
		public IllusionistsBracersAbility0(GameState state)
		{
			super(state, "Whenever an ability of equipped creature is activated, if it isn't a mana ability, copy that ability. You may choose new targets for the copy.");

			// Whenever an ability of equipped creature is activated,
			SetPattern abilities = new ActivatedAbilitiesOfPattern(EquippedBy.instance(ABILITY_SOURCE_OF_THIS));

			SimpleEventPattern activated = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			activated.put(EventType.Parameter.OBJECT, abilities);
			this.addPattern(activated);

			// if it isn't a mana ability,
			SetGenerator thatAbility = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);
			this.interveningIf = Not.instance(ManaAbilityFilter.instance(thatAbility));

			// copy that ability. You may choose new targets for the copy.
			EventFactory copy = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy that ability. You may choose new targets for the copy.");
			copy.parameters.put(EventType.Parameter.CAUSE, This.instance());
			copy.parameters.put(EventType.Parameter.OBJECT, thatAbility);
			copy.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(copy);
		}
	}

	public IllusionistsBracers(GameState state)
	{
		super(state);

		// Whenever an ability of equipped creature is activated, if it isn't a
		// mana ability, copy that ability. You may choose new targets for the
		// copy.
		this.addAbility(new IllusionistsBracersAbility0(state));

		// Equip (3)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
