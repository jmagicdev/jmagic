package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Curse of Echoes")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.CURSE, SubType.AURA})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class CurseofEchoes extends Card
{
	public static final class CurseofEchoesAbility1 extends EventTriggeredAbility
	{
		public CurseofEchoesAbility1(GameState state)
		{
			super(state, "Whenever enchanted player casts an instant or sorcery spell, each other player may copy that spell and may choose new targets for the copy he or she controls.");

			SetGenerator instantOrSorcery = HasType.instance(Type.INSTANT, Type.SORCERY);
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, EnchantedBy.instance(ABILITY_SOURCE_OF_THIS));
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(instantOrSorcery, Spells.instance()));
			this.addPattern(pattern);

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator spell = EventParameter.instance(triggerEvent, EventType.Parameter.OBJECT);
			DynamicEvaluation player = DynamicEvaluation.instance();

			EventFactory copy = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy that spell and you may choose new targets for the copy");
			copy.parameters.put(EventType.Parameter.CAUSE, This.instance());
			copy.parameters.put(EventType.Parameter.OBJECT, spell);
			copy.parameters.put(EventType.Parameter.PLAYER, player);

			EventFactory forEachPlayer = new EventFactory(FOR_EACH_PLAYER, "Each other player may copy that spell and may choose new targets for the copy he or she controls.");
			forEachPlayer.parameters.put(EventType.Parameter.CAUSE, This.instance());
			forEachPlayer.parameters.put(EventType.Parameter.TARGET, Identity.instance(player));
			forEachPlayer.parameters.put(EventType.Parameter.EFFECT, Identity.instance(youMay(copy)));
			this.addEffect(forEachPlayer);
		}
	}

	public CurseofEchoes(GameState state)
	{
		super(state);

		// Enchant player
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Player(state));

		// Whenever enchanted player casts an instant or sorcery spell, each
		// other player may copy that spell and may choose new targets for the
		// copy he or she controls.
		this.addAbility(new CurseofEchoesAbility1(state));
	}
}
