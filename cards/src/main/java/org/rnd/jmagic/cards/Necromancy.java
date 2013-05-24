package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Necromancy")
@Types({Type.ENCHANTMENT})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.VISIONS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Necromancy extends Card
{
	public static final class NecromancyAbility1 extends EventTriggeredAbility
	{
		public static final class EnchantAnimated extends org.rnd.jmagic.abilities.EnchantAnimatedCreature
		{
			public static final class Tracker extends ReanimationTracker
			{
				@Override
				protected Class<? extends TriggeredAbility> triggerClass()
				{
					return NecromancyAbility1.class;
				}
			}

			public EnchantAnimated(GameState state)
			{
				super(state, "Necromancy", new Tracker());
			}
		}

		public NecromancyAbility1(GameState state)
		{
			super(state, "When Necromancy enters the battlefield, if it's on the battlefield, it becomes an Aura with \"enchant creature put onto the battlefield with Necromancy.\" Put target creature card from a graveyard onto the battlefield under your control and attach Necromancy to it. When Necromancy leaves the battlefield, that creature's controller sacrifices it.");
			this.addPattern(whenThisEntersTheBattlefield());

			this.interveningIf = Intersect.instance(InZone.instance(Battlefield.instance()), ABILITY_SOURCE_OF_THIS);

			ContinuousEffect.Part becomesAura = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			becomesAura.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			becomesAura.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.AURA));

			ContinuousEffect.Part addAbility = addAbilityToObject(ABILITY_SOURCE_OF_THIS, EnchantAnimated.class);

			this.addEffect(createFloatingEffect("It becomes an Aura with \"enchant creature put onto the battlefield with Necromancy.\"", becomesAura, addAbility));

			SetGenerator inGraveyards = InZone.instance(GraveyardOf.instance(Players.instance()));
			SetGenerator legalTargets = Intersect.instance(HasType.instance(Type.CREATURE), inGraveyards);
			SetGenerator target = targetedBy(this.addTarget(legalTargets, "target creature card from a graveyard"));

			EventFactory putOntoBattlefield = putOntoBattlefield(target, "Put target creature card from a graveyard onto the battlefield under your control");
			this.addEffect(putOntoBattlefield);

			SetGenerator thatCreature = NewObjectOf.instance(EffectResult.instance(putOntoBattlefield));
			this.addEffect(attach(ABILITY_SOURCE_OF_THIS, thatCreature, "and attach Necromancy to it."));

			{
				thatCreature = delayedTriggerContext(thatCreature);

				SimpleZoneChangePattern pattern = new SimpleZoneChangePattern(Battlefield.instance(), null, ABILITY_SOURCE_OF_THIS, true);

				EventFactory sacrifice = sacrificeSpecificPermanents(ControllerOf.instance(thatCreature), thatCreature, "That creature's controller sacrifices it");

				EventFactory delayed = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "When Necromancy leaves the battlefield, that creature's controller sacrifices it.");
				delayed.parameters.put(EventType.Parameter.CAUSE, This.instance());
				delayed.parameters.put(EventType.Parameter.ZONE_CHANGE, Identity.instance(pattern));
				delayed.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sacrifice));
				this.addEffect(delayed);
			}
		}
	}

	public Necromancy(GameState state)
	{
		super(state);

		// You may cast Necromancy as though it had flash. If you cast it any
		// time a sorcery couldn't have been cast, the controller of the
		// permanent it becomes sacrifices it at the beginning of the next
		// cleanup step.
		this.addAbility(new Instachant(state, "Necromancy"));

		// When Necromancy enters the battlefield, if it's on the battlefield,
		// it becomes an Aura with
		// "enchant creature put onto the battlefield with Necromancy." Put
		// target creature card from a graveyard onto the battlefield under your
		// control and attach Necromancy to it. When Necromancy leaves the
		// battlefield, that creature's controller sacrifices it.
		this.addAbility(new NecromancyAbility1(state));
	}
}
